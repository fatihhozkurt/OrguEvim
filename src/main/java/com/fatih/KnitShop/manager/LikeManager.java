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

import static com.fatih.KnitShop.consts.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class LikeManager implements LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final MessageSource messageSource;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public LikeEntity getPostLikeById(UUID ownerId, UUID postId, UUID likeId) {
        return likeRepository.findByPost_User_IdAndPost_IdAndId(ownerId, postId, likeId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.LK003",
                        new Object[]{ownerId, postId, likeId},
                        Locale.getDefault())
                ));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public LikeEntity getCommentLikeById(UUID ownerId, UUID postId, UUID commentId, UUID likeId) {
        return likeRepository.findByPost_User_IdAndPost_IdAndComment_IdAndId(ownerId, postId, commentId, likeId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.LK004",
                        new Object[]{ownerId, postId, commentId, likeId},
                        Locale.getDefault())));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public LikeEntity getReplyLikeById(UUID ownerId, UUID postId, UUID commentId, UUID replyId, UUID likeId) {
        CommentEntity foundReply = commentService.getReplyById(ownerId, postId, commentId, replyId);
        return foundReply.getLikes().stream().filter(like -> like.getId().equals(likeId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(
                        "backend.exceptions.LK005",
                        new Object[]{ownerId, postId, commentId, replyId, likeId},
                        Locale.getDefault()
                )));
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getLikesByPostId(UUID ownerId, UUID postId, Pageable pageable) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);

        PostEntity foundPost = postService.getPostById(ownerId, postId);
        List<LikeEntity> foundLikes = foundPost.getLikes();
        List<UserEntity> foundUsers = foundLikes.stream().map(LikeEntity::getUser).toList();
        List<UserEntity> content = foundUsers.subList(1, foundUsers.size());
        return new PageImpl<>(content, pageable, foundLikes.size());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getLikesByCommentId(UUID ownerId, UUID postId, UUID commentId, Pageable pageable) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);
        commentService.checkComment(commentId);

        Page<LikeEntity> foundLikes =
                likeRepository.findAllByPost_User_IdAndPost_IdAndComment_Id(ownerId, postId, commentId, pageable);

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

        LikeEntity foundLike = getPostLikeById(ownerId, postId, likeId);

        checkAuthority(foundLike.getUser().getId(), requesterId);

        foundLike.setRecordStatus(PASSIVE);

        foundLike.getPost().setLikeCount(foundLike.getPost().getLikeCount() - 1);

        return likeRepository.save(foundLike);
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
        //Beğenilecek yorum
        CommentEntity foundComment = commentService.getCommentById(ownerId, postId, commentId);

        //Bu yorum daha önce beğenilmiş mi?
        validateCommentLikes(foundComment, requesterId);

        LikeEntity newLike = LikeEntity.builder()
                .comment(foundComment)
                .user(foundUser)
                .post(foundComment.getPost())
                .build();

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

        LikeEntity foundLike = getCommentLikeById(ownerId, postId, commentId, likeId);

        checkAuthority(foundLike.getUser().getId(), requesterId);

        foundLike.getComment().setLikeCount(foundLike.getComment().getLikeCount() - 1);

        foundLike.setRecordStatus(PASSIVE);

        return likeRepository.save(foundLike);
    }

    @Transactional
    @Override
    public LikeEntity likeReply(UUID ownerId, UUID postId, UUID commentId, UUID replyId, UUID requesterId) {

        UserEntity foundUser = userService.getUserById(requesterId);
        userService.checkUser(ownerId);
        PostEntity foundPost = postService.getPostById(ownerId, postId);
        CommentEntity foundReply = commentService.getReplyById(ownerId, postId, commentId, replyId);

        validateReplyLikes(foundReply, requesterId);

        LikeEntity newLike = LikeEntity.builder()
                .comment(foundReply)
                .user(foundUser)
                .post(foundPost)
                .build();

        foundReply.getLikes().add(newLike);
        foundReply.setLikeCount((long) foundReply.getLikes().size());

        return likeRepository.save(newLike);
    }


    @Transactional
    @Override
    public LikeEntity unlikeReply(UUID ownerId, UUID postId, UUID commentId, UUID replyId, UUID likeId, UUID requesterId) {

        userService.checkUser(ownerId);
        userService.checkUser(requesterId);
        postService.checkPost(postId);
        commentService.checkComment(commentId);

        LikeEntity foundLike = getReplyLikeById(ownerId, postId, commentId, replyId, likeId);

        checkAuthority(foundLike.getUser().getId(), requesterId);

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

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validateReplyLikes(CommentEntity foundReply, UUID requesterId) {

        boolean isAlreadyLiked = foundReply.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(requesterId));

        if (isAlreadyLiked) {
            throw new DataAlreadyExistException(messageSource.getMessage(
                    "backend.exceptions.LK002",
                    new Object[]{requesterId},
                    Locale.getDefault()));
        }
    }
}