package org.codecollad.snapverse.controllers;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.PostDTO;
import org.codecollad.snapverse.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
  @Autowired
  private PostService postService;

  @GetMapping
  public ResponseEntity<?> getAll() {
    ApiResponse<?> response = postService.getAll();
    return new ResponseEntity<>(response, HttpStatus.OK);
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

}
