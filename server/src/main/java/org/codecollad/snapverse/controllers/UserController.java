package org.codecollad.snapverse.controllers;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.UserDTO;
import org.codecollad.snapverse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;


    @GetMapping("/{id}/me")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        ApiResponse<?> response = userService.getProfile(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<?>> updateProfile(
        @PathVariable Long id, @RequestBody UserDTO user) {
        ApiResponse<?> response = userService.updateProfile(id, user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
