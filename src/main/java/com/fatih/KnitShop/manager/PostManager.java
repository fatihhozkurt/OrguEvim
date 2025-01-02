package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.CategoryEntity;
import com.fatih.KnitShop.entity.ImageEntity;
import com.fatih.KnitShop.entity.PostEntity;
import com.fatih.KnitShop.entity.UserEntity;
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
    private final SoftDeletePostRelationsManager softDeletePostRelationsManager;

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

        userService.getUserById(ownerId);

        return postRepository.findByUser_IdAndId(ownerId, postId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.P001",
                        new Object[]{ownerId, postId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<PostEntity> getPostsByUserId(UUID ownerId, Pageable pageable) {

        userService.getUserById(ownerId);

        return postRepository.findByUser_Id(ownerId, pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<PostEntity> getPostsByCategoryId(UUID categoryId, Pageable pageable) {

        categoryService.getCategoryById(categoryId);

        return postRepository.findByCategory_Id(categoryId, pageable);
    }


    @Transactional
    @Override
    public PostEntity createPost(PostEntity requestedPost, UUID requesterId) {

        UserEntity userEntity = userService.getUserById(requestedPost.getUser().getId());
        CategoryEntity categoryEntity = categoryService.getCategoryById(requestedPost.getCategory().getId());

        checkAuthority(requestedPost.getUser().getId(), requesterId);

        PostEntity post = PostEntity.builder()
                .title(requestedPost.getTitle())
                .content(requestedPost.getContent())
                .ingredients(requestedPost.getIngredients())
                .youtubeLink(requestedPost.getYoutubeLink())
                .user(userEntity)
                .category(categoryEntity).build();

        List<ImageEntity> postImages = requestedPost.getImages();
        post.setImages(postImages);
        post.setImageCount((long) postImages.size());

        ImageEntity coverImage = requestedPost.getImages().getFirst();
        post.setCoverImage(coverImage);

        userEntity.getPosts().add(post);
        categoryEntity.getPosts().add(post);

        return postRepository.save(post);
    }

    @Transactional
    @Override
    public void deletePost(UUID ownerId, UUID postId, UUID requesterId) {

        //İstemci ve hedef kullanıcıyı doğrula
        userService.getUserById(ownerId);
        userService.getUserById(requesterId);

        //İstemci ve hedef kullanıcı aynı kişi mi?
        checkAuthority(ownerId, requesterId);

        //İlgili gönderiyi bul
        PostEntity foundPost = getPostById(ownerId, postId);

        //İlişkili varlıkları sil
        softDeletePostRelationsManager.softDeletePostRelations(foundPost);

        foundPost.setRecordStatus(PASSIVE);

        postRepository.save(foundPost);
    }

    @Transactional
    @Override
    public PostEntity updatePost(PostEntity requestedPost, UUID requesterId) {

        userService.getUserById(requestedPost.getUser().getId());
        userService.getUserById(requesterId);

        PostEntity foundPost = getPostById(requestedPost.getId(), requesterId);

        checkAuthority(requestedPost.getId(), requesterId);

        return updateChecks(requestedPost, foundPost);
    }

    @Override
    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkAuthority(UUID ownerId, UUID requesterId) {
        if (!(ownerId.equals(requesterId))) {
            throw new UnauthorizedException(messageSource.getMessage("backend.exceptions.U006",
                    new Object[]{requesterId, ownerId},
                    Locale.getDefault()));
        }
    }

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
            List<ImageEntity> imageEntity = requestedPost.getImages();
            foundPost.setImages(imageEntity);
        }

        return postRepository.save(foundPost);
    }
}