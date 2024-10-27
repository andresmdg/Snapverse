package org.codecollad.snapverse.services;

import org.codecollad.snapverse.models.dto.ApiResponse;

public interface LikeService {
  ApiResponse<Object> add(Long userId, Long postId);
  ApiResponse<Object> remove(Long userId, Long postId);
}
