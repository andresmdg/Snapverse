package org.codecollad.snapverse.controllers;

import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> add(@RequestBody User user) {
    try {
      userService.add(user);

      ApiResponse<Void> response = new ApiResponse<>(true, 201, "User added");

      return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (Exception e) {
      ApiResponse<Void> response = new ApiResponse<>(false, 401, e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

}
