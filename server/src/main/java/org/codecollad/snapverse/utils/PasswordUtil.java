package org.codecollad.snapverse.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public static String hashPassword(String password) {
    return encoder.encode(password);
  }

  public static boolean verifyPassword(String rawPassword, String hashPassword) {
    return encoder.matches(rawPassword, hashPassword);
  }
}
