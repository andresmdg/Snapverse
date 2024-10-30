package org.codecollad.snapverse.services;

import java.util.Optional;

import org.codecollad.snapverse.exceptions.custom.user.UserNotFoundException;
import org.codecollad.snapverse.exceptions.custom.user.UsernameAlreadyExistsException;
import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.UserDTO;
import org.codecollad.snapverse.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public ApiResponse<Object> getProfile(Long id) {
        User user = userJpaRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserDTO userDTO = UserDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .lastname(user.getLastname())
            .username(user.getUsername())
            .build();

        return ApiResponse.builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User found")
                .user(userDTO)
                .build();
    }

    @Override
    public ApiResponse<Object> updateProfile(Long id, UserDTO user) {
        Optional<User> existingUser = userJpaRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User updateUser = existingUser.get();

        if (!updateUser.getUsername().equals(user.getUsername())
                && userJpaRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already taken");
        }

        updateUser.setName(user.getName());
        updateUser.setLastname(user.getLastname());
        updateUser.setUsername(user.getUsername());
        userJpaRepository.save(updateUser);

        UserDTO respUser = UserDTO.builder()
            .id(updateUser.getId())
            .name(updateUser.getName())
            .lastname(updateUser.getLastname())
            .username(updateUser.getUsername())
            .build();

        return ApiResponse.builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User profile updated successfully")
                .user(respUser)
                .build();
    }
}
