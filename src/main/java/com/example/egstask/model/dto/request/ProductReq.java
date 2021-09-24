package com.example.egstask.model.dto.request;

import com.example.egstask.model.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductReq {

    @NotBlank
    private String name;
    @DecimalMin("1.0")
    private double price;
    @JsonIgnore
    private Category category;

}
