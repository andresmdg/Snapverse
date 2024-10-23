package org.codecollad.snapverse.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

  private String body;
  private Long userId;
  private boolean liked;
}
