package org.codecollad.snapverse.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
  private Long id;
  private String body;
  private Long userId;
  private List<LikeDTO> likes;
  private int likeCount;

  public CommentDTO(Long id, String body, Long userId) {
    this.id = id;
    this.body = body;
    this.userId = userId;
  }
}
