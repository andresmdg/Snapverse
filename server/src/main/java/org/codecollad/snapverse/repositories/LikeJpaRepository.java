package org.codecollad.snapverse.repositories;

import org.codecollad.snapverse.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {
  int countByPostId(Long postId);
  int countByCommentId(Long commentId);

  boolean existsByUserIdAndPostId(Long userId, Long postId);

  void deleteByUserIdAndPostId(Long userId, Long postId);
}
