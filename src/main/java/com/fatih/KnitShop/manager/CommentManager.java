package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.CommentEntity;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.manager.service.CommentService;
import com.fatih.KnitShop.manager.service.PostService;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

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

        return commentRepository.findByUser_IdAndPost_IdAndId(ownerId, postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("backend.exceptions.COM002",
                                new Object[]{ownerId, postId, commentId},
                                Locale.getDefault())));
    }
}
