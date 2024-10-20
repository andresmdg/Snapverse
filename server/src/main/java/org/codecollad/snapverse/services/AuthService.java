package org.codecollad.snapverse.services;

import java.util.Map;

import org.codecollad.snapverse.models.dto.LoginDTO;

public interface AuthService {

    Map<String, String> login(LoginDTO login);
}
