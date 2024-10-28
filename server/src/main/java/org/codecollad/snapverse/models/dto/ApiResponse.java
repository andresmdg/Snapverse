package org.codecollad.snapverse.models.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private Boolean success;
  private Integer statusCode;
  private HttpStatus status;
  private String message;
  private String details;
  private String token;
  private List<?> data;
  private PostResponseDTO post;
  private Integer likeCount;
  private UserDTO user;

}