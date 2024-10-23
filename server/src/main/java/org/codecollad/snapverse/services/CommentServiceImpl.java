package org.codecollad.snapverse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.codecollad.snapverse.exceptions.custom.PostNotFoundException;
import org.codecollad.snapverse.exceptions.custom.UserNotFoundException;
import org.codecollad.snapverse.models.Comment;
import org.codecollad.snapverse.models.Post;
import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.CommentDTO;
import org.codecollad.snapverse.models.dto.LikeDTO;
import org.codecollad.snapverse.repositories.CommentJpaRepository;
import org.codecollad.snapverse.repositories.LikeJpaRepository;
import org.codecollad.snapverse.repositories.PostJpaRepository;
import org.codecollad.snapverse.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private LikeJpaRepository likeJpaRepository;

  @Autowired
  private CommentJpaRepository commentJpaRepository;

  @Autowired
  private PostJpaRepository postJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  public ApiResponse<Object> getAll(Long postId) {
    try {
      List<Comment> comments = commentJpaRepository.findByPostId(postId);
      List<CommentDTO> commentDTOs = new ArrayList<>();

      for (Comment comment : comments) {
        List<LikeDTO> likeDTOs = comment.getLikes().stream()
            .map(like -> new LikeDTO(like.getId(), like.getUser().getId(), comment.getId()))
            .collect(Collectors.toList());

        int likeCount = countLikes(comment.getId());

        CommentDTO commentDTO = new CommentDTO(
            comment.getId(),
            comment.getBody(),
            comment.getUser().getId(),
            likeDTOs,
            likeCount);
        commentDTOs.add(commentDTO);
      }

      return ApiResponse.builder()
          .statusCode(HttpStatus.OK.value())
          .status(HttpStatus.OK)
          .message("Comments found")
          .data(commentDTOs)
          .build();
    } catch (Exception e) {
      return ApiResponse.builder()
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .message("An error has occurred: " + e.getMessage())
          .build();
    }
  }

  public ApiResponse<Object> add(CommentDTO commentDTO, Long postId, Long userId) {
    Post post = postJpaRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post not found"));

    // Obtener el usuario por el userId
    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    Comment comment = new Comment();
    comment.setBody(commentDTO.getBody());
    comment.setPost(post);
    comment.setUser(user);

    commentJpaRepository.save(comment);

    return ApiResponse.builder()
        .success(true)
        .statusCode(HttpStatus.CREATED.value())
        .message("Comment added successfully")
        .build();
  }

  public ApiResponse<Object> remove(Long commentId) {
    if (commentJpaRepository.existsById(commentId)) {
      commentJpaRepository.deleteById(commentId);
      return ApiResponse.builder()
          .success(true)
          .statusCode(HttpStatus.OK.value())
          .message("Comment removed successfully")
          .build();
    } else {
      return ApiResponse.builder()
          .success(false)
          .statusCode(HttpStatus.NOT_FOUND.value())
          .message("Comment not found")
          .build();
    }
  }

  public int countLikes(Long commentId) {
    return likeJpaRepository.countByCommentId(commentId);
  }

}
