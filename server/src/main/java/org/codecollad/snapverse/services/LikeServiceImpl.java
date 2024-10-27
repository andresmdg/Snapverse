package org.codecollad.snapverse.services;

import org.codecollad.snapverse.exceptions.custom.PostNotFoundException;
import org.codecollad.snapverse.exceptions.custom.UserNotFoundException;
import org.codecollad.snapverse.models.Like;
import org.codecollad.snapverse.models.Post;
import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.repositories.LikeJpaRepository;
import org.codecollad.snapverse.repositories.PostJpaRepository;
import org.codecollad.snapverse.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

  @Autowired
  private PostJpaRepository postJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Autowired
  private LikeJpaRepository likeJpaRepository;

  public ApiResponse<Object> add(Long userId, Long postId) {
    System.out.println("Received userId: " + userId + ", postId: " + postId);
    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    Post post = postJpaRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post not found"));

    // Verifica si ya existe el like
    if (likeJpaRepository.existsByUserIdAndPostId(userId, postId)) {
      return ApiResponse.builder()
          .success(false)
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .message("User already liked this post")
          .build();
    }

    Like like = new Like();
    like.setUser(user);
    like.setPost(post);
    likeJpaRepository.save(like);

    return ApiResponse.builder()
        .success(true)
        .statusCode(HttpStatus.CREATED.value())
        .message("Like added successfully")
        .build();
  }

  @Transactional
  public ApiResponse<Object> remove(Long userId, Long postId) {
    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    Post post = postJpaRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post not found"));

    if (!likeJpaRepository.existsByUserIdAndPostId(userId, postId)) {
      return ApiResponse.builder()
          .success(false)
          .statusCode(HttpStatus.NOT_FOUND.value())
          .message("Like not found for this user and post")
          .build();
    }

    likeJpaRepository.deleteByUserIdAndPostId(userId, postId);

    return ApiResponse.builder()
        .success(true)
        .statusCode(HttpStatus.OK.value())
        .message("Like removed successfully")
        .build();
  }

}
