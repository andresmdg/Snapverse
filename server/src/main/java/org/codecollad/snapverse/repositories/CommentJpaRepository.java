package org.codecollad.snapverse.repositories;

import java.util.List;

import org.codecollad.snapverse.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPostId(Long postId);
}
