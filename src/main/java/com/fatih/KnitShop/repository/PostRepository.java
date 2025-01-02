package com.fatih.KnitShop.repository;

import com.fatih.KnitShop.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    Optional<PostEntity> findByUser_IdAndId(UUID userId, UUID postId);

    Page<PostEntity> findByUser_Id(UUID ownerId, Pageable pageable);

    Page<PostEntity> findByCategory_Id(UUID categoryId, Pageable pageable);
}
