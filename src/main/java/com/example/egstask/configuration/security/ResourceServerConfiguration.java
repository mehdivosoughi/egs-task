package com.example.egstask.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET,"/categories").authenticated()
                .antMatchers("/categories").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST,"/categories/{\\d+}/products").hasAuthority("ROLE_ADMIN")
                .antMatchers( "/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated();
    }

}
