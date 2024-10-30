package org.codecollad.snapverse.controllers;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.PostDTO;
import org.codecollad.snapverse.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping
  public ResponseEntity<?> getAllPosts(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
    ApiResponse<?> response = postService.getAll(page, size);
    return new ResponseEntity<>(response, response.getStatus());
  }

  @PostMapping
  public ResponseEntity<?> add(@RequestBody PostDTO post) {
    ApiResponse<?> response = postService.add(post);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPost(@PathVariable Long id) {
    ApiResponse<?> response = postService.getPost(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    ApiResponse<?> response = postService.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
