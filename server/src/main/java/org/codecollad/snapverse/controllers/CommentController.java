package org.codecollad.snapverse.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.CommentDTO;
import org.codecollad.snapverse.services.CommentService;
import org.codecollad.snapverse.utils.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;
  @Autowired
  private JwtUtility jwtUtility;

  @GetMapping("/{postId}")
  public ApiResponse<Object> getAll(@PathVariable Long postId) {
    return commentService.getAll(postId);
  }

  @PostMapping("/{postId}")
  public ApiResponse<Object> add(
      @RequestBody CommentDTO commentDTO,
      @PathVariable Long postId,
      @RequestHeader("Authorization") String token)
      throws ParseException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

    Long userId = jwtUtility.extractUserId(token);
    return commentService.add(commentDTO, postId, userId);
  }

  @DeleteMapping("/{commentId}")
  public ApiResponse<Object> remove(@PathVariable Long commentId) {
    return commentService.remove(commentId);
  }
}
