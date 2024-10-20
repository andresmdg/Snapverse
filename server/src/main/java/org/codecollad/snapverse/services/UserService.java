package org.codecollad.snapverse.services;

import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.repositories.UserRepository;
import org.codecollad.snapverse.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User add(User user) {

    if (userRepository.existsByUsername(user.getUsername())) {
      throw new RuntimeException("User already exists");
    }

    user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
    return userRepository.save(user);
  }

  public boolean verifyCredentials (String username, String rawPassword) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    return PasswordUtil.verifyPassword(rawPassword, user.getPassword());
  }

}
