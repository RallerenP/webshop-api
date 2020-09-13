package com.rpovlsen.webshopapi.user;

public class User
{
    private String username;
    private String password;
    private final int id;
    private boolean isAdmin;

    public User(int id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
