package com.rpovlsen.webshopapi.auth;

import com.rpovlsen.webshopapi.auth.dto.UserDTO;
import com.rpovlsen.webshopapi.auth.exceptions.NotAuthorizedException;
import com.rpovlsen.webshopapi.auth.service.AuthService;
import com.rpovlsen.webshopapi.auth.service.IAuthService;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.entity.IUser;
import com.rpovlsen.webshopapi.user.exceptions.UsernameTakenException;
import com.rpovlsen.webshopapi.user.repository.IUserRepository;
import com.rpovlsen.webshopapi.user.service.IUserService;
import org.apache.coyote.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/auth")
public class AuthController
{

    private final IAuthService authService;
    private final IUserService userService;

    // AuthModule is a bean provider, spring uses the beans for DI
    @Autowired
    public AuthController(IAuthService authService, IUserService userService)
    {
        this.authService = authService;
        this.userService = userService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    ResponseEntity<String> login(@Valid @RequestBody UserDTO userDTO)
    {
        JSONObject resp = new JSONObject();
        try
        {

            resp.put("jwt", this.authService.login(userDTO));
            return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
        }
        catch (NotAuthorizedException e)
        {
            resp.put("error", "Incorrect username or password!");
            return new ResponseEntity<>(resp.toString(), HttpStatus.UNAUTHORIZED);
        }


    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    ResponseEntity<String> signup(@Valid @RequestBody UserDTO userDTO)
    {
        JSONObject resp = new JSONObject();
        try
        {
            IUser user = this.userService.createUser(userDTO);
            if (user == null)
            {
                resp.put("error", "User could not be created!");
                return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else
            {
                resp.put("success", "User was created!");
                return new ResponseEntity<>(resp.toString(), HttpStatus.CREATED);
            }
        }
        catch (UsernameTakenException e)
        {
            resp.put("error", "username is taken!");
            return new ResponseEntity<>(resp.toString(), HttpStatus.CONFLICT);
        }
    }

}
