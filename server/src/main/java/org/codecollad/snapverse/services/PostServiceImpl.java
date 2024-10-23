package org.codecollad.snapverse.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.codecollad.snapverse.exceptions.custom.UserNotFoundException;
import org.codecollad.snapverse.models.Comment;
import org.codecollad.snapverse.models.Post;
import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.CommentDTO;
import org.codecollad.snapverse.models.dto.LikeDTO;
import org.codecollad.snapverse.models.dto.PostDTO;
import org.codecollad.snapverse.models.dto.PostResponseDTO;
import org.codecollad.snapverse.repositories.LikeJpaRepository;
import org.codecollad.snapverse.repositories.PostJpaRepository;
import org.codecollad.snapverse.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    } catch (DataAccessException e) {
      return ApiResponse.builder()
          .success(false)
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .message("Database error: " + e.getMessage())
          .build();
    } catch (UserNotFoundException e) {
      return ApiResponse.builder()
          .success(false)
          .statusCode(HttpStatus.NOT_FOUND.value())
          .status(HttpStatus.NOT_FOUND)
          .message(e.getMessage())
          .build();
    } catch (Exception e) {
      return ApiResponse.builder()
          .success(false)
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .message("An error has occurred: " + e.getMessage())
          .build();
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
      List<LikeDTO> likeDTOs = post.getLikes().stream()
          .map(like -> new LikeDTO(like.getId(), like.getUser().getId(), post.getId()))
          .collect(Collectors.toList());

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

      PostResponseDTO postResponse = new PostResponseDTO(
          post.getId(),
          post.getBody(),
          likeDTOs,
          post.getCreatedAt(),
          post.getAttachments(),
          commentDTOs,
          likeCount);

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
  public ApiResponse<Object> getAll() {
    try {
      List<Post> posts = postJpaRepository.findAllByOrderByCreatedAtDesc();
      List<PostResponseDTO> postResponses = new ArrayList<>();

      for (Post post : posts) {
        int likeCount = likeJpaRepository.countByPostId(post.getId());

        List<LikeDTO> likeDTOs = post.getLikes().stream()
            .map(like -> new LikeDTO(like.getId(), like.getUser().getId(), post.getId()))
            .collect(Collectors.toList());

        // Obtener solo los últimos 3 comentarios
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

        PostResponseDTO postResponse = new PostResponseDTO(
            post.getId(),
            post.getBody(),
            likeDTOs,
            post.getCreatedAt(),
            post.getAttachments(),
            commentDTOs,
            likeCount);
        postResponses.add(postResponse);
      }

      return ApiResponse.builder()
          .statusCode(HttpStatus.OK.value())
          .status(HttpStatus.OK)
          .message("Posts found")
          .data(postResponses)
          .build();
    } catch (Exception e) {
      return ApiResponse.builder()
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .message("An error has occurred: " + e.getMessage())
          .build();
    }
  }

  public int countLikes(Long postId) {
    return likeJpaRepository.countByPostId(postId);
  }

}
