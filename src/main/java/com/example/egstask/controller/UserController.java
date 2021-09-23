package com.example.egstask.controller;

import com.example.egstask.model.dto.request.LoginReq;
import com.example.egstask.model.dto.request.RegisterUserReq;
import com.example.egstask.model.dto.response.LoginRes;
import com.example.egstask.model.dto.response.RegisterUserRes;
import com.example.egstask.model.dto.response.Response;
import com.example.egstask.model.entity.EgsUser;
import com.example.egstask.model.entity.Role;
import com.example.egstask.model.service.RoleService;
import com.example.egstask.model.service.UserService;
import com.example.egstask.model.service.security.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final Oauth2Service oauth2Service;

    @Autowired
    public UserController(UserService userService, RoleService roleService, Oauth2Service oauth2Service) {
        this.userService = userService;
        this.roleService = roleService;
        this.oauth2Service = oauth2Service;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<RegisterUserRes>> register(HttpServletRequest request,
                                                              @Valid @RequestBody RegisterUserReq userDto) {
        Role role = roleService.findByRole(userDto.getRole().toString());
        EgsUser user = userService.registerUser(userDto, role);
        RegisterUserRes registerUserRes = new RegisterUserRes(user);
        Response<RegisterUserRes> response = new Response<RegisterUserRes>("OK", request.getRequestURI()).setMessage(registerUserRes);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginRes>> login(HttpServletRequest request,
                                                    @Valid @RequestBody LoginReq loginReq) {
        EgsUser user = userService.login(loginReq);
        LoginRes loginRes = new LoginRes(user);
        loginRes.setToken(oauth2Service.getToken(loginReq.getUsername(), loginReq.getPassword(), user.getRoles()));
        Response<LoginRes> response = new Response<LoginRes>("OK", request.getRequestURI()).setMessage(loginRes);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
