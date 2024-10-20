package org.codecollad.snapverse.repositories;

import java.util.Optional;

import org.codecollad.snapverse.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
