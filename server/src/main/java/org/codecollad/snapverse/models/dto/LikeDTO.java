package org.codecollad.snapverse.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
  private Long id;
  private Long userId;
  private Long postId;
}
