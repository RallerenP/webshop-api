package com.rpovlsen.webshopapi.user.repository;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;

import java.util.List;

public interface IUserRepository
{
    User create(UserDTO userDTO);

    User getById(int id) throws UserNotFoundException;
    User findOne(String col, int search) throws UserNotFoundException;
    User findOne(String col, String search) throws UserNotFoundException;

    List<User> find();
    List<User> find(String col, int search);
    List<User> find(String col, String search);

    User update(int id, UserDTO userDTO) throws UserNotFoundException;

    boolean delete(int id) throws UserNotFoundException;
}
