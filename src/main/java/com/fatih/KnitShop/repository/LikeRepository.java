package com.fatih.KnitShop.repository;

import com.fatih.KnitShop.entity.LikeEntity;
import com.fatih.KnitShop.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
    List<LikeEntity> post(PostEntity post);

    Page<LikeEntity> findAllByPost_User_IdAndPost_Id(UUID ownerId, UUID postId, Pageable pageable);

    Page<LikeEntity> findAllByPost_User_IdAndPost_IdAndComment_Id(UUID ownerId,
                                                                  UUID postId,
                                                                  UUID commentId,
                                                                  Pageable pageable);

    Optional<LikeEntity> findByPost_User_IdAndPost_IdAndId(UUID ownerId, UUID postId, UUID likeId);

    Optional<LikeEntity> findByPost_User_IdAndPost_IdAndComment_IdAndId(UUID ownerId,
                                                                        UUID postId,
                                                                        UUID commentId,
                                                                        UUID likeId);
}
