package com.rpovlsen.webshopapi.config;

import com.rpovlsen.webshopapi.auth.AuthHandlerInterceptor;
import com.rpovlsen.webshopapi.user.GetUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebMvc
public class Config implements WebMvcConfigurer {

    private AuthHandlerInterceptor authHandlerInterceptor;
    private GetUserResolver getUserResolver;

    @Autowired
    Config(AuthHandlerInterceptor authHandlerInterceptor, GetUserResolver getUserResolver)
    {
        this.authHandlerInterceptor = authHandlerInterceptor;
        this.getUserResolver = getUserResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authHandlerInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers)
    {
        resolvers.add(getUserResolver);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5000")
                .allowedMethods("POST", "GET", "PUT", "PATCH", "DELETE")
                .allowCredentials(true).maxAge(3600);
    }
}
