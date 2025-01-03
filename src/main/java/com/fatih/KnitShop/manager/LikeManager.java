package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.CommentEntity;
import com.fatih.KnitShop.entity.LikeEntity;
import com.fatih.KnitShop.entity.PostEntity;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.exception.DataAlreadyExistException;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.exception.UnauthorizedException;
import com.fatih.KnitShop.manager.service.CommentService;
import com.fatih.KnitShop.manager.service.LikeService;
import com.fatih.KnitShop.manager.service.PostService;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.LikeRepository;
import com.fatih.KnitShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class LikeManager implements LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final MessageSource messageSource;
    private final UserRepository userRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public LikeEntity getPostLikeById(UUID ownerId, UUID postId, UUID likeId) {
        return likeRepository.findByUser_IdAndPost_IdAndId(ownerId, postId, likeId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.LK003",
                        new Object[]{ownerId, postId, likeId},
                        Locale.getDefault())
                ));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public LikeEntity getCommentLikeById(UUID ownerId, UUID postId, UUID commentId, UUID likeId) {
        return likeRepository.findByUser_IdAndPost_IdAndComment_IdAndId(ownerId, postId, commentId, likeId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.LK004",
                        new Object[]{ownerId, postId, commentId, likeId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getLikesByPostId(UUID ownerId, UUID postId, Pageable pageable) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);

        Page<LikeEntity> foundLikes = likeRepository.findAllByUser_IdAndPost_Id(ownerId, postId, pageable);
        List<UserEntity> foundUsers = foundLikes.getContent().stream().map(LikeEntity::getUser).toList();
        return new PageImpl<>(foundUsers, pageable, foundLikes.getTotalElements());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getLikesByCommentId(UUID ownerId, UUID postId, UUID commentId, Pageable pageable) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);
        commentService.checkComment(commentId);

        Page<LikeEntity> foundLikes =
                likeRepository.findAllByUser_IdAndPost_IdAndComment_Id(ownerId, postId, commentId, pageable);

        List<UserEntity> foundUsers = foundLikes.getContent().stream().map(LikeEntity::getUser).toList();
        return new PageImpl<>(foundUsers, pageable, foundLikes.getTotalElements());
    }

    @Transactional
    @Override
    public LikeEntity likePost(UUID ownerId, UUID postId, UUID requesterId) {

        //Postun sahibi
        userService.checkUser(ownerId);
        //Post
        postService.checkPost(postId);
        //Beğenen kişi
        userService.checkUser(requesterId);

        //OwnerId ile işim bitti. Postumu buldum
        PostEntity foundPost = postService.getPostById(ownerId, postId);

        //Beğeniyi yapacak kullanıcıs
        UserEntity requester = userService.getUserById(requesterId);

        validatePostLikes(foundPost, requesterId);

        LikeEntity newLike = LikeEntity.builder()
                .post(foundPost)
                .user(requester)
                .build();

        foundPost.getLikes().add(newLike);
        foundPost.setLikeCount((long) foundPost.getLikes().size());

        return likeRepository.save(newLike);
    }

    @Transactional
    @Override
    public LikeEntity unlikePost(UUID ownerId, UUID postId, UUID likeId, UUID requesterId) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);
        userService.checkUser(requesterId);

        checkAuthority(ownerId, requesterId);

        LikeEntity likeEntity = getPostLikeById(ownerId, postId, likeId);

        likeEntity.setRecordStatus(PASSIVE);

        likeEntity.getPost().setLikeCount(likeEntity.getPost().getLikeCount() - 1);

        return likeRepository.save(likeEntity);
    }

    @Transactional
    @Override
    public LikeEntity likeComment(UUID ownerId, UUID postId, UUID commentId, UUID requesterId) {

        //Post'un sahibi
        userService.checkUser(ownerId);
        //Post
        postService.checkPost(postId);
        //Comment
        commentService.checkComment(commentId);
        //Comment'i beğenmek isteyen kullanıcı
        UserEntity foundUser = userService.getUserById(requesterId);

        CommentEntity foundComment = commentService.getCommentById(ownerId, postId, commentId);

        validateCommentLikes(foundComment, requesterId);

        LikeEntity newLike = LikeEntity.builder()
                .comment(foundComment)
                .user(foundUser).build();

        foundComment.getLikes().add(newLike);
        foundComment.setLikeCount((long) foundComment.getLikes().size());

        return likeRepository.save(newLike);
    }

    @Transactional
    @Override
    public LikeEntity unlikeComment(UUID ownerId, UUID postId, UUID commentId, UUID likeId, UUID requesterId) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);
        commentService.checkComment(commentId);
        userService.checkUser(requesterId);

        checkAuthority(ownerId, requesterId);

        LikeEntity foundLike = getCommentLikeById(ownerId, postId, commentId, likeId);

        foundLike.getComment().setLikeCount(foundLike.getComment().getLikeCount() - 1);

        foundLike.setRecordStatus(PASSIVE);

        return likeRepository.save(foundLike);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkAuthority(UUID ownerId, UUID requestedId) {
        if (!(ownerId.equals(requestedId))) {
            throw new UnauthorizedException(messageSource.getMessage("backend.exceptions.LK001",
                    new Object[]{requestedId, ownerId},
                    Locale.getDefault()));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validatePostLikes(PostEntity foundPost, UUID requesterId) {
        boolean isAlreadyLiked = foundPost.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(requesterId));

        if (isAlreadyLiked) {
            throw new DataAlreadyExistException(messageSource.getMessage(
                    "backend.exceptions.LK002",
                    new Object[]{requesterId},
                    Locale.getDefault())
            );
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validateCommentLikes(CommentEntity foundComment, UUID requesterId) {

        boolean isAlreadyLiked = foundComment.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(requesterId));

        if (isAlreadyLiked) {
            throw new DataAlreadyExistException(messageSource.getMessage(
                    "backend.exceptions.LK002",
                    new Object[]{requesterId},
                    Locale.getDefault()));
        }
    }
}