package com.example.egstask.model.service;

import com.example.egstask.exception.UnauthorizedException;
import com.example.egstask.model.entity.EgsUser;
import com.example.egstask.model.entity.security.CustomUserPrincipal;
import com.example.egstask.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.egstask.common.ErrorMessages.NOT_FOUND;
import static com.example.egstask.common.ErrorMessages.UNAUTHORIZED;

@Service("userDetailService")
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("here");
        Optional<EgsUser> optional = userRepository.findByUsername(username);
        if (!optional.isPresent())
            throw new UsernameNotFoundException(NOT_FOUND);
        EgsUser user = optional.get();
        if (user.getLocked())
            throw new UnauthorizedException(UNAUTHORIZED);
        return new CustomUserPrincipal(optional.get());
    }

}
