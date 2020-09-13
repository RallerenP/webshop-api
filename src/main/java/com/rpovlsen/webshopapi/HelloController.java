package com.rpovlsen.webshopapi;

import com.rpovlsen.webshopapi.auth.annotations.guards.AuthGuard;
import com.rpovlsen.webshopapi.user.User;
import com.rpovlsen.webshopapi.user.annotations.GetUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HelloController
{
    @GetMapping("/hello")
    @AuthGuard()
    public ResponseEntity<String> getHello(@GetUser() User user)
    {
        return new ResponseEntity<>("Hello " + user.getUsername(), HttpStatus.OK);
    }
}
