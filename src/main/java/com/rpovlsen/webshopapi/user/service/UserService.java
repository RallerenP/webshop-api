package com.rpovlsen.webshopapi.user.service;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.entity.IUser;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;
import com.rpovlsen.webshopapi.user.exceptions.UsernameTakenException;
import com.rpovlsen.webshopapi.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class UserService implements IUserService
{
    private final List<User> users = new ArrayList<>();

    private final IUserRepository userRepository;

    @Autowired()
    public UserService(IUserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public IUser getUserByUsername(String username) throws UserNotFoundException
    {
        return this.userRepository.findOne("username", username);
    }

    @Override
    public IUser createUser(UserDTO userDTO) throws UsernameTakenException
    {
        try
        {
            this.userRepository.findOne("username", userDTO.getUsername());
            throw new UsernameTakenException();
        }
        catch (UserNotFoundException e)
        {
            return this.userRepository.create(userDTO);
        }

    }

    @Override
    public IUser updateUser(UserDTO userDTO, User user)
            throws UsernameTakenException, UserNotFoundException
    {
        try
        {
            this.userRepository.findOne("username", userDTO.getUsername());
            throw new UsernameTakenException();
        }
        catch (UserNotFoundException e)
        {
            return this.userRepository.update(user.getId(), userDTO);
        }
    }

    @Override
    public IUser getUserById(int id) throws UserNotFoundException {
        return this.userRepository.getById(id);
    }
}
