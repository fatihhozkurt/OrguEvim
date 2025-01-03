package com.fatih.KnitShop.repository;

import com.fatih.KnitShop.entity.LikeEntity;
import com.fatih.KnitShop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsUserEntityByUsername(String username);

    boolean existsUserEntityByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}