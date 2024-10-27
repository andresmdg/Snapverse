package org.codecollad.snapverse.services;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.CommentDTO;

public interface CommentService {
  
  ApiResponse<Object> add(CommentDTO commentDTO, Long postId, Long userId);

  ApiResponse<Object> remove(Long commentId);

  ApiResponse<Object> getAll(Long postId);
}
