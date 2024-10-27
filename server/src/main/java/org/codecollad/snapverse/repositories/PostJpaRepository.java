package org.codecollad.snapverse.repositories;

import java.util.List;

import org.codecollad.snapverse.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByOrderByCreatedAtDesc();
}
