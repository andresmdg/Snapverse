package org.codecollad.snapverse.controllers;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {
  @Autowired
  private LikeService likeService;

  @PostMapping
  public ApiResponse<Object> add(@RequestParam Long userId, @RequestParam Long postId) {
    return likeService.add(userId, postId);
  }

  @DeleteMapping
  public ApiResponse<Object> remove(@RequestParam Long userId, @RequestParam Long postId) {
    return likeService.add(userId, postId);
  }
}
