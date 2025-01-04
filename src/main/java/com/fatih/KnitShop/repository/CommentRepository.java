package com.fatih.KnitShop.repository;

import com.fatih.KnitShop.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Optional<CommentEntity> findByPost_User_IdAndPost_IdAndId(UUID ownerId, UUID postId, UUID commentId);

    Page<CommentEntity> findAllByPost_User_IdAndPost_Id(UUID ownerId, UUID postId, Pageable pageable);

    Optional<CommentEntity> findByPost_User_IdAndPost_IdAndSupComment_IdAndId(UUID ownerId, UUID postId, UUID commentId, UUID replyId);

    @Query("SELECT c FROM CommentEntity c WHERE c.post.user.id = :ownerId AND c.post.id = :postId  AND c.supComment.id = :commentId")
    Page<CommentEntity> findByPost_User_IdAndPost_IdAndSupComment_Id(UUID ownerId, UUID postId, UUID commentId, Pageable pageable);
}
