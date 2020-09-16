package com.rpovlsen.webshopapi.user.entity;

public interface IUser
{
    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);

    int getId();
}
