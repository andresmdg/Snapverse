package org.codecollad.snapverse.services;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.PostDTO;

import java.awt.print.Pageable;

public interface PostService {

  ApiResponse<Object> getAll(int page, int size);

  ApiResponse<Object> getPost(Long id);

  ApiResponse<Object> add(PostDTO post);

  ApiResponse<Object> delete(Long id);

}
