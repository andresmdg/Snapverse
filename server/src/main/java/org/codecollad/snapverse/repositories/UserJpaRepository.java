package org.codecollad.snapverse.repositories;

import java.util.Optional;

import org.codecollad.snapverse.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
