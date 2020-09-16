package com.rpovlsen.webshopapi.user.repository;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.entity.IUser;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;

import java.util.List;

public interface IUserRepository
{
    IUser create(UserDTO userDTO);

    IUser getById(int id) throws UserNotFoundException;
    IUser findOne(String col, int search) throws UserNotFoundException;
    IUser findOne(String col, String search) throws UserNotFoundException;

    List<IUser> find();
    List<IUser> find(String col, int search);
    List<IUser> find(String col, String search);

    IUser update(int id, UserDTO userDTO) throws UserNotFoundException;

    boolean delete(int id) throws UserNotFoundException;
}
