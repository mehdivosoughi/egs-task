package com.example.egstask.model.dto.request;

import com.example.egstask.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterUserReq {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Size(min = 3, message = "Password must be minimum 3 characters")
    private String password;

    @NotNull(message = "Role cannot be empty")
    private RoleEnum role;

}
