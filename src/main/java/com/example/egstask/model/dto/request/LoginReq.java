package com.example.egstask.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginReq {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
