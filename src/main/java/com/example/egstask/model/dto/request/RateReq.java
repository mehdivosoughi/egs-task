package com.example.egstask.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class RateReq {

    @JsonIgnore
    private long userId;
    @JsonIgnore
    private long productId;
    @Min(1)
    @Max(5)
    private int rate;
    private String comment;

}
