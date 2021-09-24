package com.example.egstask.model.dto.response;

import com.example.egstask.model.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRes {

    private long id;
    private String name;
    private double price;
    private double rate;

    public ProductRes(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.rate = product.getRate();
    }

}
