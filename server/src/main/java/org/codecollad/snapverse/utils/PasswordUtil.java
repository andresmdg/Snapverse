package org.codecollad.snapverse.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
  private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String hashPassword(String password) {
    return encoder.encode(password);
  }

  public static boolean verifyPassword(String rawPassword, String hashPassword) {
    return encoder.matches(rawPassword, hashPassword);
  }
}
