package com.rpovlsen.webshopapi.auth.service;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.auth.exceptions.NotAuthorizedException;

public interface IAuthService
{
    String login(UserDTO userDTO) throws NotAuthorizedException;

    boolean signup(UserDTO userDTO);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token);
}
