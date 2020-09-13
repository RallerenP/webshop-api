package com.rpovlsen.webshopapi.auth.annotations.guards;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthGuard // Meta-data annotation to specify a valid login (jwt) requirement for handling a request.
{

}
