package com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.repository;

import com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository  extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
