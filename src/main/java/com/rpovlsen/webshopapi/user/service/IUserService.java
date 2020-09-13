package com.rpovlsen.webshopapi.user.service;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;
import com.rpovlsen.webshopapi.user.exceptions.UsernameTakenException;

public interface IUserService
{
    User getUserByUsername(String username) throws UserNotFoundException;
    User getUserById(int id) throws UserNotFoundException;

    User createUser(UserDTO userDTO) throws UsernameTakenException;
    User updateUser(UserDTO userDTO, User user) throws UsernameTakenException, UserNotFoundException;
}
