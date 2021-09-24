package com.example.egstask.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CategoryReq {

    @NotBlank
    private String name;

}
