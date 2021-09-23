package com.example.egstask.model.service;

import com.example.egstask.common.ErrorMessages;
import com.example.egstask.model.entity.EgsUser;
import com.example.egstask.model.entity.security.CustomUserPrincipal;
import com.example.egstask.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailService")
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<EgsUser> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(ErrorMessages.NOT_FOUND);
        }
        return new CustomUserPrincipal(user.get());
    }

}
