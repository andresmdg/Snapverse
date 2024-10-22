package org.codecollad.snapverse.services;

import org.codecollad.snapverse.models.User;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.LoginDTO;

public interface AuthService {

    ApiResponse<Object> login (LoginDTO login);

    ApiResponse<Object> register (User user);

}
