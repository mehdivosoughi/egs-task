package com.example.egstask.controller;

import com.example.egstask.model.dto.response.Response;
import com.example.egstask.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/lockUser/{userId}")
    public ResponseEntity<Response> lockUser(HttpServletRequest request, @PathVariable Long userId) {
        userService.lockUser(userId);
        Response response = new Response("OK", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/unlockUser/{userId}")
    public ResponseEntity<Response> unLockUser(HttpServletRequest request, @PathVariable Long userId) {
        userService.unLockUser(userId);
        Response response = new Response("OK", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
