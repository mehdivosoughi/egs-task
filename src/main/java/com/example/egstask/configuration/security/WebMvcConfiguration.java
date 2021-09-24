package com.example.egstask.configuration.security;

import com.example.egstask.interceptor.PrincipalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final PrincipalInterceptor principalInterceptor;

    public WebMvcConfiguration(PrincipalInterceptor principalInterceptor) {
        this.principalInterceptor = principalInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(principalInterceptor);
    }

}
