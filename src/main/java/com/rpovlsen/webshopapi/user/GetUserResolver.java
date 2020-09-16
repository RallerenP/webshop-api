package com.rpovlsen.webshopapi.user;

import com.rpovlsen.webshopapi.auth.service.IAuthService;
import com.rpovlsen.webshopapi.user.annotations.GetUser;
import com.rpovlsen.webshopapi.user.entity.IUser;
import com.rpovlsen.webshopapi.user.exceptions.UserNotFoundException;
import com.rpovlsen.webshopapi.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class GetUserResolver implements HandlerMethodArgumentResolver
{
    private final IUserService userService;
    private final IAuthService authService;

    @Autowired()
    GetUserResolver (IUserService userService, IAuthService authService)
    {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.hasParameterAnnotation(GetUser.class);
    }

    @Override
    public IUser resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        GetUser getUser = parameter.getParameterAnnotation(GetUser.class);

        // If empty, get the logged in user, by the token.
        if (getUser.username().isBlank())
        {
            // All methods with @GetUser should be AuthGuarded, so the auth token is present and valid.
            HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

            String token = request.getHeader("Authorization").replace("Bearer ", "");

            String username = authService.getUsernameFromToken(token);

            try
            {
                return this.userService.getUserByUsername(username);
            }
            catch (UserNotFoundException e)
            {
                return null;
            }
        }
        else
        {
            try
            {
                return this.userService.getUserByUsername(getUser.username());
            }
            catch (UserNotFoundException e)
            {
                return null;
            }
        }
    }
}
