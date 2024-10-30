package org.codecollad.snapverse.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import org.codecollad.snapverse.models.Attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {

  private Long id;
  private String body;
  private LocalDateTime createdAt;
  private List<Attachment> attachments;
  private List<CommentDTO> comments;
  private int likesCount;

}
