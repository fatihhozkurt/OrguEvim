package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.*;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.exception.UnauthorizedException;
import com.fatih.KnitShop.manager.service.CategoryService;
import com.fatih.KnitShop.manager.service.PostService;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class PostManager implements PostService {

    private final PostRepository postRepository;
    private final MessageSource messageSource;
    private final UserService userService;
    private final CategoryService categoryService;
    private final SoftDeletePost softDeletePost;

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<PostEntity> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<PostEntity> getRandomPosts(Pageable pageable) {

        List<PostEntity> foundPosts = new ArrayList<>(postRepository.findAll(pageable).getContent());
        Collections.shuffle(foundPosts);

        return new PageImpl<>(foundPosts, pageable, foundPosts.size());
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public PostEntity getPostById(UUID ownerId, UUID postId) {

        userService.checkUser(ownerId);

        return postRepository.findByUser_IdAndId(ownerId, postId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.PST002",
                        new Object[]{ownerId, postId},
                        Locale.getDefault())));
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<PostEntity> getPostsByUserId(UUID ownerId, Pageable pageable) {

        userService.checkUser(ownerId);

        return postRepository.findByUser_Id(ownerId, pageable);
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<PostEntity> getPostsByCategoryId(UUID categoryId, Pageable pageable) {

        categoryService.getCategoryById(categoryId);

        return postRepository.findByCategory_Id(categoryId, pageable);
    }


    //Checked
    @Transactional
    @Override
    public PostEntity createPost(PostEntity requestedPost, UUID requesterId) {
        //İlgili user ve category'i bul
        UserEntity foundUser = userService.getUserById(requestedPost.getUser().getId());
        CategoryEntity foundCategory = categoryService.getCategoryById(requestedPost.getCategory().getId());

        //İki user aynı mı kontrol et
        checkAuthority(requesterId, requestedPost.getUser().getId());

        //Alanları tek tek setle
        PostEntity newPost = PostEntity.builder()
                .title(requestedPost.getTitle())
                .content(requestedPost.getContent())
                .ingredients(requestedPost.getIngredients())
                .youtubeLink(requestedPost.getYoutubeLink())
                .user(foundUser)
                .category(foundCategory).build();

        //User oluşturulurken verilmesi gereken postImages listesi ilişkili bir entity olduğu için newleyip setle
        requestedPost.getImages().forEach(image -> image.setPost(newPost));
        List<ImageEntity> newImages = requestedPost.getImages();

        newPost.setImages(newImages);
        newPost.setImageCount((long) newImages.size());

        //Cover image aynı şekilde
        ImageEntity coverImage = requestedPost.getImages().getFirst();
        newPost.setCoverImage(coverImage);

        //UserEntity'nin postlarına oluşturulan postu ekle
        foundUser.getPosts().add(newPost);
        foundUser.setPostCount((long) foundUser.getPosts().size());

        //CategoryEntity'nin postlarına oluşturulan postu ekle
        foundCategory.getPosts().add(newPost);
        foundCategory.setPostCount((long) foundCategory.getPosts().size());

        return postRepository.save(newPost);
    }

    //Checked
    @Transactional
    @Override
    public void deletePost(UUID ownerId, UUID postId, UUID requesterId) {

        //İstemci ve hedef kullanıcıyı doğrula
        userService.checkUser(ownerId);
        userService.checkUser(requesterId);

        //İstemci ve hedef kullanıcı aynı kişi mi?
        checkAuthority(requesterId, ownerId);

        //İlgili gönderiyi bul
        PostEntity foundPost = getPostById(ownerId, postId);

        //İlişkili varlıkları sil
        softDeletePost.softDeletePost(foundPost);
    }

    //Checked
    @Transactional
    @Override
    public PostEntity updatePost(PostEntity requestedPost, UUID requesterId) {

        userService.checkUser(requestedPost.getUser().getId());
        userService.checkUser(requesterId);

        PostEntity foundPost = getPostById(requesterId, requestedPost.getId());

        checkAuthority(requesterId, requestedPost.getUser().getId());

        return updateChecks(requestedPost, foundPost);
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkAuthority(UUID requesterId, UUID ownerId) {
        if (!(ownerId.equals(requesterId))) {
            throw new UnauthorizedException(messageSource.getMessage("backend.exceptions.PST003",
                    new Object[]{requesterId, ownerId},
                    Locale.getDefault()));
        }
    }

    //Checked
    @Transactional
    public PostEntity updateChecks(PostEntity requestedPost, PostEntity foundPost) {
        if (requestedPost.getTitle() != null) {
            foundPost.setTitle(requestedPost.getTitle());
        }
        if (requestedPost.getIngredients() != null) {
            foundPost.setIngredients(requestedPost.getIngredients());
        }
        if (requestedPost.getContent() != null) {
            foundPost.setContent(requestedPost.getContent());
        }
        if (requestedPost.getYoutubeLink() != null) {
            foundPost.setYoutubeLink(requestedPost.getYoutubeLink());
        }
        if (requestedPost.getImages() != null) {
            foundPost.getImages().forEach(image -> image.setRecordStatus(PASSIVE));

            List<ImageEntity> newImages = requestedPost.getImages();
            newImages.forEach(image -> image.setPost(foundPost));
            foundPost.getImages().addAll(newImages);

            foundPost.setImageCount(foundPost.getImages().stream().filter(BaseEntity::isRecordStatus).count());
            foundPost.setCoverImage(newImages.getFirst());
        }
        return postRepository.save(foundPost);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void checkPost(UUID postId) {
        Optional.of(postRepository.existsById(postId)).filter(exists -> exists).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.PST001",
                        new Object[]{postId},
                        Locale.getDefault())));
    }
}