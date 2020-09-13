package com.rpovlsen.webshopapi.auth;

import com.rpovlsen.webshopapi.auth.annotations.guards.AuthGuard;
import com.rpovlsen.webshopapi.auth.service.AuthService;
import com.rpovlsen.webshopapi.auth.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthHandlerInterceptor extends HandlerInterceptorAdapter
{
    private final IAuthService authService;

    @Autowired()
    public AuthHandlerInterceptor(IAuthService authService)
    {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        // Handle Annotations
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // Handle AuthGuard annotations
            if (handlerMethod.hasMethodAnnotation(AuthGuard.class))
            {
                String jwtToken = request.getHeader("Authorization");

                // If no JWT token was provided.
                if (jwtToken == null) // Don't handle request.
                {
                    response.setStatus(401);
                    return false;
                }

                if (!jwtToken.startsWith("Bearer ")) // Don't handle if authorization token is malformed
                {
                    response.setStatus(401);
                    return false;
                }

                jwtToken = jwtToken.replace("Bearer ", ""); // Remove bearer (just keep token)

                if (!this.authService.isTokenValid(jwtToken)) // Token needs to be regenerated.
                {
                    response.setStatus(401);
                    return false;
                }

                return true;
            };
        }

        return true;
    }
}
