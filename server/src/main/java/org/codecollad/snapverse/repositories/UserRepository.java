package org.codecollad.snapverse.repositories;

import java.util.Optional;

import org.codecollad.snapverse.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

}