package com.rpovlsen.webshopapi.user.repository;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository()
public class UserRepository implements IUserRepository
{

    private final JdbcTemplate template;

    @Autowired()
    public UserRepository(JdbcTemplate template)
    {
        this.template = template;
    }

    @Override
    public User create(UserDTO userDTO)
    {
        String SQL = "INSERT INTO users (username, password) VALUES (?,?)";
        KeyHolder kh = new GeneratedKeyHolder();

        // Lamba expression to "modify" template update to allow getting back the generated key.
        int rows = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, userDTO.getUsername());
            ps.setString(2, userDTO.getPassword());

            return ps;
        }, kh);

        if (rows == 0)
        {
            return null;
        }
        else
        {
            try
            {
                return getById(kh.getKey().intValue()); // Key will logically not be null
            }
            catch (UserNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }

        }
    }

    public User findOne()
    {
        return null;
    }

    @Override
    public User getById(int id) throws UserNotFoundException
    {
        String SQL = "SELECT * FROM users WHERE id = ?";
        SqlRowSet result = template.queryForRowSet(SQL, id);

        if (result.next())
        {
            return load(result);
        }
        else
        {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User findOne(String col, int search) throws UserNotFoundException {
        String SQL = "SELECT * FROM users WHERE "+ col + " = ?";
        SqlRowSet result = template.queryForRowSet(SQL, col, search);

        if (result.next())
        {
            return load(result);
        }
        else
        {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User findOne(String col, String search) throws UserNotFoundException {
        String SQL = "SELECT * FROM users WHERE "+ col + " = ?";
        SqlRowSet result = template.queryForRowSet(SQL, search);

        if (result.next())
        {
            return load(result);
        }
        else
        {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> find() {
        String SQL = "SELECT * FROM users";
        SqlRowSet result = template.queryForRowSet(SQL);

        List<User> users = new ArrayList<>();

        while (result.next()) users.add(load(result));

        return users;
    }

    @Override
    public List<User> find(String col, int search) {
        String SQL = "SELECT * FROM users WHERE ? = ?";
        SqlRowSet result = template.queryForRowSet(SQL, col, search);

        List<User> users = new ArrayList<>();

        while (result.next()) users.add(load(result));

        return users;
    }

    public List<User> find(String col, String search)
    {
        String SQL = "SELECT * FROM users WHERE "+ col + " = ?";
        SqlRowSet result = template.queryForRowSet(SQL, col, search);

        List<User> users = new ArrayList<>();

        while (result.next()) users.add(load(result));

        return users;
    }

    @Override
    public User update(int id, UserDTO userDTO) throws UserNotFoundException
    {
        getById(id); // To propagate UserNotFoundException. We don't actually need to save.

        String SQL = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        template.update(SQL, userDTO.getUsername(), userDTO.getPassword(), id); // Could check if rows are updated by saving returned value

        return getById(id);
    }

    @Override
    public boolean delete(int id) throws UserNotFoundException
    {
        getById(id); // To propagate UserNotFoundException. We don't actually need to save.

        String SQL = "DELETE FROM users WHERE id = ?";
        if(template.update(SQL, id) != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public User load(SqlRowSet rowset)
    {
        User u = new User(rowset.getInt("id"));
        u.setUsername(rowset.getString("username"));
        u.setPassword(rowset.getString("password"));
        return u;
    }
}
