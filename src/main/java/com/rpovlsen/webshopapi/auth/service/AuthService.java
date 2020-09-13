package com.rpovlsen.webshopapi.auth.service;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.auth.exceptions.NotAuthorizedException;
import com.rpovlsen.webshopapi.auth.jwt.JwtTokenUtil;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.service.UserService;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service()
public class AuthService implements IAuthService
{
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthService(JwtTokenUtil jwtTokenUtil, UserService userService)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    public String login(UserDTO userDTO) throws NotAuthorizedException
    {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        try
        {
            User entity = this.userService.getUserByUsername(userDTO.getUsername());

            if (entity.getPassword().equals(password))
                return jwtTokenUtil.genToken(new HashMap<>(), username); // Empty HashMap for now, we don't have additional claims to register
            else
                throw new NotAuthorizedException();
        }
        catch (UserNotFoundException e)
        {
            throw new NotAuthorizedException(); // Don't tell user the username doesn't exist.
        }
    }

    @Override
    public boolean signup(UserDTO userDTO) {
        return false;
    }

    public String getUsernameFromToken(String token)
    {
        return this.jwtTokenUtil.getUsernameFromToken(token);
    }

    public boolean isTokenValid(String token)
    {
        return this.jwtTokenUtil.isTokenValid(token);
    }
}
