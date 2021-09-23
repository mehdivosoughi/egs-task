package com.example.egstask.model.service;

import com.example.egstask.exception.BadRequestException;
import com.example.egstask.exception.NotFoundException;
import com.example.egstask.model.dto.request.LoginReq;
import com.example.egstask.model.dto.request.RegisterUserReq;
import com.example.egstask.model.entity.EgsUser;
import com.example.egstask.model.entity.Role;
import com.example.egstask.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static com.example.egstask.common.ErrorMessages.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public EgsUser registerUser(RegisterUserReq userDto, Role role) {
        Optional<EgsUser> optional = userRepository.findByUsername(userDto.getUsername());
        if (optional.isPresent()) {
            throw new BadRequestException(BAD_REQUEST);
        }
        EgsUser user = EgsUser.builder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .created(new Date())
                .updated(new Date())
                .roles(new HashSet<>(Collections.singletonList(role)))
                .build();
        user = userRepository.save(user);
        return user;
    }

    public EgsUser login(LoginReq loginReq) {
        Optional<EgsUser> optional = userRepository.findByUsername(loginReq.getUsername());
        if (!optional.isPresent())
            throw new NotFoundException(NOT_FOUND);
        EgsUser user = optional.get();
        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword()))
            throw new BadRequestException(INVALID_PASSWORD);
        return user;
    }
}
