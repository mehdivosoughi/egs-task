package com.example.egstask.model.entity;

import com.example.egstask.model.dto.request.RateReq;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rating {

    @EmbeddedId
    private RatingKey id;

    @Column
    private int rate;

    @Column
    private String comment;

    public Rating(RateReq rateReq) {
        this.id = new RatingKey(rateReq.getUserId(), rateReq.getProductId());
        this.rate = rateReq.getRate();
        this.comment = rateReq.getComment();
    }

}
