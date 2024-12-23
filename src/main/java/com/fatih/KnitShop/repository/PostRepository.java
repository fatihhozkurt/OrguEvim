package com.fatih.KnitShop.repository;

import com.fatih.KnitShop.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
}
