package org.codecollad.snapverse.repositories;

import java.util.List;

import org.codecollad.snapverse.models.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

  @NotNull Page<Post> findAll(@NotNull Pageable pageable);

  List<Post> findAllByOrderByCreatedAtDesc();
}
