package org.codecollad.snapverse.services;

import java.util.*;
import java.util.stream.Collectors;

import org.codecollad.snapverse.exceptions.custom.post.PostNotFoundException;
import org.codecollad.snapverse.exceptions.custom.user.UserNotFoundException;
import org.codecollad.snapverse.models.Comment;
import org.codecollad.snapverse.models.Post;
import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.*;
import org.codecollad.snapverse.repositories.CommentJpaRepository;
import org.codecollad.snapverse.repositories.LikeJpaRepository;
import org.codecollad.snapverse.repositories.PostJpaRepository;
import org.codecollad.snapverse.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostJpaRepository postJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Autowired
  private LikeJpaRepository likeJpaRepository;

  @Autowired
  private CommentJpaRepository commentJpaRepository;

  @Override
  public ApiResponse<Object> add(PostDTO postDTO) {
    try {
      User user = userJpaRepository.findById(postDTO.getUserId())
          .orElseThrow(() -> new UserNotFoundException("User not found"));

      Post post = new Post();
      post.setBody(postDTO.getBody());
      post.setUser(user);

      postJpaRepository.save(post);

      return ApiResponse.builder()
          .success(true)
          .statusCode(HttpStatus.CREATED.value())
          .status(HttpStatus.CREATED)
          .message("Post created successfully")
          .build();
    } catch (UserNotFoundException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new RuntimeException("Unexpected error", ex); 
    }
  }

  @Override
  public ApiResponse<Object> delete(Long id) {
    if (postJpaRepository.existsById(id)) {
      postJpaRepository.deleteById(id);
      return ApiResponse.builder()
          .success(true)
          .statusCode(HttpStatus.OK.value())
          .status(HttpStatus.OK)
          .message("Post deleted successfully")
          .build();
    } else {
      return ApiResponse.builder()
          .success(false)
          .statusCode(HttpStatus.NOT_FOUND.value())
          .status(HttpStatus.NOT_FOUND)
          .message("Post not found")
          .build();
    }
  }

  @Override
  public ApiResponse<Object> getPost(Long id) {
    Post post = postJpaRepository.findById(id).orElse(null);
    if (post == null) {
      return ApiResponse.builder()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .status(HttpStatus.NOT_FOUND)
          .message("Post not found")
          .build();
    } else {
      int likeCount = countLikes(id);

      // Obtener los últimos 3 comentarios
      List<CommentDTO> commentDTOs = post.getComments().stream()
          .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
          .limit(3)
          .map(comment -> new CommentDTO(
              comment.getId(),
              comment.getBody(),
              comment.getUser().getId(),
              comment.getLikes().stream()
                  .map(like -> new LikeDTO(like.getId(), like.getUser().getId(), comment.getId()))
                  .collect(Collectors.toList()),
              likeCount))
          .collect(Collectors.toList());

      PostResponseDTO postResponse = PostResponseDTO.builder()
              .id(post.getId())
              .body(post.getBody())
              .createdAt(post.getCreatedAt())
              .attachments(post.getAttachments())
              .comments(commentDTOs)
              .likesCount(likeCount).build();

      return ApiResponse.builder()
          .statusCode(HttpStatus.OK.value())
          .status(HttpStatus.OK)
          .message("Post found")
          .post(postResponse)
          .likeCount(likeCount)
          .build();
    }
  }

  @Override
  public ApiResponse<Object> getAll(int page, int size) {
    try {
      Pageable pageable = PageRequest.of(page, size);

      Page<Post> postsPage = postJpaRepository.findAll(pageable);

      List<Map<String, Object>> responseData = postsPage.stream().map(post -> {
        User user = post.getUser();

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastname(user.getLastname())
                .build();

        PostDTO postDTO = PostDTO.builder()
                .id(post.getId())
                .userId(user.getId())
                .body(post.getBody())
                .comments(post.getComments())
                .createAt(post.getCreatedAt())
                .likesCount(likeJpaRepository.countByPostId(post.getId()))
                .commentsCount(commentJpaRepository.countByPostId(post.getId()))
                .build();

        Map<String, Object> entry = new HashMap<>();
        entry.put("user", userDTO);
        entry.put("post", postDTO);

        return entry;
      }).collect(Collectors.toList());

      return ApiResponse.builder()
              .success(true)
              .statusCode(HttpStatus.OK.value())
              .status(HttpStatus.OK)
              .message("All posts retrieved successfully")
              .data(responseData)
              .build();

    } catch (Exception e) {
      return ApiResponse.builder()
              .success(false)
              .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .message("An error occurred: " + e.getMessage())
              .build();
    }
  }


  public int countLikes(Long postId) {
    return likeJpaRepository.countByPostId(postId);
  }

}
