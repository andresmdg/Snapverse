package org.codecollad.snapverse.services;

import java.util.*;

import org.codecollad.snapverse.exceptions.InvalidCredentialsException;
import org.codecollad.snapverse.exceptions.UserAlreadyExistsException;
import org.codecollad.snapverse.exceptions.UserNotFoundException;
import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.LoginDTO;
import org.codecollad.snapverse.repositories.UserJpaRepository;
import org.codecollad.snapverse.utils.JwtUtility;
import org.codecollad.snapverse.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private JwtUtility jwtUtilityService;

    @Override
    public ApiResponse<Object> login (LoginDTO login) {
        try {
            Optional<User> user = userRepository.findByUsername(login.getUsername());
            if (user.isEmpty()) {
                throw new UserNotFoundException("User not registered!");
            }

            if (PasswordUtil.verifyPassword(login.getPassword(), user.get().getPassword())) {
                String token = jwtUtilityService.generateJWT(user.get().getId());
                return ApiResponse.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Login successful")
                        .token(token)
                        .build();
            } else {
                throw new InvalidCredentialsException("Invalid credentials");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @Override
    public ApiResponse<Object> register (User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        PasswordUtil.hashPassword(user.getPassword());
        userRepository.save(user);
        return ApiResponse.builder()
                .success(true)
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("User created successfully")
                .token(null)
                .data(List.of(user))
                .build();
    }

}
