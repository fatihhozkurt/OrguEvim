package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.CommentEntity;
import com.fatih.KnitShop.entity.PostEntity;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.exception.RecursiveCommentException;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.exception.UnauthorizedException;
import com.fatih.KnitShop.manager.service.CommentService;
import com.fatih.KnitShop.manager.service.PostService;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.CommentRepository;
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
import java.util.Optional;
import java.util.UUID;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class CommentManager implements CommentService {

    private final CommentRepository commentRepository;
    private final MessageSource messageSource;
    private final UserService userService;
    private final PostService postService;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void checkComment(UUID commentId) {
        Optional.of(commentRepository.existsById(commentId)).filter(exists -> exists).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.COM001",
                        new Object[]{commentId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CommentEntity getCommentById(UUID ownerId, UUID postId, UUID commentId) {
        userService.checkUser(ownerId);
        postService.checkPost(postId);

        return commentRepository.findByPost_User_IdAndPost_IdAndId(ownerId, postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("backend.exceptions.COM003",
                                new Object[]{ownerId, postId, commentId},
                                Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CommentEntity getReplyById(UUID ownerId, UUID postId, UUID commentId, UUID replyId) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);
        checkComment(commentId);

        CommentEntity foundComment = getCommentById(ownerId, postId, commentId);

        return foundComment.getSubComments().stream()
                .filter(reply -> reply.getId().equals(replyId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.COM004",
                        new Object[]{ownerId, postId, commentId, replyId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CommentEntity> getCommentsByPostId(UUID ownerId, UUID postId, Pageable pageable) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);

        return commentRepository.findAllByPost_User_IdAndPost_Id(ownerId, postId, pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CommentEntity> getRepliesByCommentId(UUID ownerId, UUID postId, UUID commentId, Pageable pageable) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);
        checkComment(commentId);

        CommentEntity foundComment = getCommentById(ownerId, postId, commentId);
        List<CommentEntity> foundReplies = foundComment.getSubComments();

        List<CommentEntity> content = foundReplies.subList(0, Math.min(foundReplies.size(), pageable.getPageSize()));

        return new PageImpl<>(content, pageable, foundReplies.size());
    }

    @Transactional
    @Override
    public CommentEntity createComment(UUID ownerId, UUID postId, UUID requesterId, String content) {

        userService.checkUser(ownerId);
        PostEntity foundPost = postService.getPostById(ownerId, postId);
        UserEntity foundUser = userService.getUserById(requesterId);

        CommentEntity newComment = CommentEntity.builder()
                .post(foundPost)
                .user(foundUser)
                .content(content)
                .build();

        foundPost.getComments().add(newComment);
        foundUser.getComments().add(newComment);

        foundPost.setCommentCount((long) foundPost.getComments().size());

        return commentRepository.save(newComment);
    }

    @Transactional
    @Override
    public CommentEntity createReply(UUID ownerId, UUID postId, UUID commentId, UUID requesterId, String content) {

        userService.checkUser(ownerId);
        postService.checkPost(postId);
        UserEntity foundUser = userService.getUserById(requesterId);
        CommentEntity foundComment = getCommentById(ownerId, postId, commentId);

        checkRecursive(foundComment);

        CommentEntity newReply = CommentEntity.builder()
                .user(foundUser)
                .supComment(foundComment)
                .content(content)
                .build();

        foundUser.getComments().add(newReply);
        foundComment.getSubComments().add(newReply);
        foundComment.setReplyCount((long) foundComment.getSubComments().size());

        return commentRepository.save(newReply);
    }

    @Transactional
    @Override
    public void deleteComment(UUID ownerId, UUID postId, UUID commentId, UUID requesterId) {

        userService.checkUser(ownerId);
        userService.checkUser(requesterId);
        postService.checkPost(postId);
        checkComment(commentId);

        CommentEntity foundComment = getCommentById(ownerId, postId, commentId);

        checkAuthority(foundComment.getUser().getId(), requesterId);

        softDeleteComment(foundComment);

        commentRepository.save(foundComment);
    }

    @Transactional
    @Override
    public void deleteReply(UUID ownerId, UUID postId, UUID commentId, UUID replyId, UUID requesterId) {

        userService.checkUser(ownerId);
        userService.checkUser(requesterId);
        postService.checkPost(postId);
        checkComment(commentId);

        CommentEntity foundReply = getReplyById(ownerId, postId, commentId, replyId);

        checkAuthority(foundReply.getUser().getId(), requesterId);

        softDeleteReply(foundReply);

        commentRepository.save(foundReply);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkAuthority(UUID requesterId, UUID ownerId) {
        if (!(ownerId.equals(requesterId))) {
            throw new UnauthorizedException(messageSource.getMessage("backend.exceptions.COM002",
                    new Object[]{requesterId, ownerId},
                    Locale.getDefault()));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkRecursive(CommentEntity foundComment) {
        if (foundComment.getSupComment() != null) {
            throw new RecursiveCommentException(messageSource.getMessage("backend.exceptions.COM005",
                    new Object[]{},
                    Locale.getDefault()));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void softDeleteComment(CommentEntity foundComment) {
        if (foundComment.getLikes() != null) {
            foundComment.getLikes().forEach(like -> like.setRecordStatus(PASSIVE));
        }
        if (foundComment.getSubComments() != null) {
            foundComment.getSubComments().forEach(this::softDeleteReply);
        }
        foundComment.getPost().setCommentCount(foundComment.getPost().getCommentCount() - 1);

        foundComment.setRecordStatus(PASSIVE);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public void softDeleteReply(CommentEntity foundReply) {
        foundReply.getLikes().forEach(like -> like.setRecordStatus(PASSIVE));
        foundReply.getSupComment().setReplyCount(foundReply.getSupComment().getReplyCount() - 1);

        foundReply.setRecordStatus(PASSIVE);
    }
}