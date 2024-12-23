package com.fatih.KnitShop.repository;

import com.fatih.KnitShop.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
}
