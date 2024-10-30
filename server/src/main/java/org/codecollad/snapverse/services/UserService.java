package org.codecollad.snapverse.services;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.codecollad.snapverse.models.dto.UserDTO;

public interface UserService {
    
    ApiResponse<Object> getProfile(Long id);

    ApiResponse<Object> updateProfile(Long id, UserDTO user);
}
