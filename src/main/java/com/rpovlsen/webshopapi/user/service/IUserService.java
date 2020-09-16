package com.rpovlsen.webshopapi.user.service;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.entity.IUser;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;
import com.rpovlsen.webshopapi.user.exceptions.UsernameTakenException;

public interface IUserService
{
    IUser getUserByUsername(String username) throws UserNotFoundException;
    IUser getUserById(int id) throws UserNotFoundException;

    IUser createUser(UserDTO userDTO) throws UsernameTakenException;
    IUser updateUser(UserDTO userDTO, User user) throws UsernameTakenException, UserNotFoundException;
}
