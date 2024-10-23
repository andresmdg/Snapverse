package org.codecollad.snapverse.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.codecollad.snapverse.models.Attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
  private Long id;
  private String body;
  private List<LikeDTO> likes;
  private LocalDateTime createdAt;
  private List<Attachment> attachments;
  private List<CommentDTO> comments;
  private int likeCount;
}
