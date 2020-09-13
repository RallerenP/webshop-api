package com.rpovlsen.webshopapi.user.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add to controller method parameter to get the current user from the JWT token, or to get an user based on the provided username.
 * Controller should be guarded with @AuthGuard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface GetUser
{
    String username() default "";
}
