package com.fatih.KnitShop.repository;

import com.fatih.KnitShop.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
}
