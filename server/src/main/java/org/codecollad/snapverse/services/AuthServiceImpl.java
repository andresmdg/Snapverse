package org.codecollad.snapverse.services;

import java.util.*;

import org.codecollad.snapverse.models.UserEntity;
import org.codecollad.snapverse.models.dto.LoginDTO;
import org.codecollad.snapverse.repositories.UserJpaRepository;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private JwtUtilityService jwtUtilityService;

    @Override
    public Map<String, String> login(LoginDTO login) {
        try {
            Map<String, String> resp = new HashMap<>();
            Optional<UserEntity> user = userRepository.findByUsername(login.getUsername());

            if (user.isEmpty()) {
                resp.put("msg", "User not register!");
                return resp;
            }

            if (verifyPassword(login.getPassword(), user.get().getPassword())) {
                resp.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
            } else {
                resp.put("msg", "Authentication failed");
            }
            return resp;
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }

}
