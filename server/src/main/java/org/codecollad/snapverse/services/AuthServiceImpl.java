package org.codecollad.snapverse.services;

import java.util.*;

import org.codecollad.snapverse.exceptions.custom.user.InvalidCredentialsException;
import org.codecollad.snapverse.exceptions.custom.user.TokenGenerationException;
import org.codecollad.snapverse.exceptions.custom.user.UserAlreadyExistsException;
import org.codecollad.snapverse.exceptions.custom.user.UserNotFoundException;
import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.LoginDTO;
import org.codecollad.snapverse.models.dto.UserDTO;
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
    public ApiResponse<Object> login(LoginDTO login) {
        try {
            Optional<User> user = userRepository.findByUsername(login.getUsername());
            if (user.isEmpty()) {
                throw new UserNotFoundException("User not registered!");
            }

            System.out.println(user.toString());
            System.out.println(login.getUsername().toString());
            System.out.println(login.getPassword().toString());
            
            if (PasswordUtil.verifyPassword(login.getPassword(), user.get().getPassword())) {
                try {
                    System.out.println("El password y el username si coincidieron");

                    UserDTO userDTO = UserDTO.builder()
                    .id(user.get().getId())
                    .name(user.get().getName())
                    .lastname(user.get().getLastname())
                    .username(user.get().getUsername()).build();
                    String token = jwtUtilityService.generateJWT(user.get().getId());
                    System.out.println("El token es:" + token);
                    return ApiResponse.builder()
                            .success(true)
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .message("Login successful")
                            .user(userDTO)
                            .token(token)
                            .build();
                } catch (TokenGenerationException e) {
                    System.out.println("El token no se genero");
                    throw new TokenGenerationException("Error generating token", e);
                }
            } else {
                throw new InvalidCredentialsException("Invalid credentials");
            }
        } catch (UserNotFoundException | InvalidCredentialsException | TokenGenerationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected error during login", ex);
        }
    }

    @Override
    public ApiResponse<Object> register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String hashedPass = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPass);
        
        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("User created successfully")
                .build();
    }

}
