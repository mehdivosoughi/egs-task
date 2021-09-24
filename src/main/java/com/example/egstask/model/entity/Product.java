package com.example.egstask.model.entity;

import com.example.egstask.model.dto.request.ProductReq;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "name_index", columnList = "name"),
        @Index(name = "price_index", columnList = "price"),
        @Index(name = "rate_index", columnList = "rate")})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private long id;

    @Column
    private String name;

    @Column
    private double price;

    @Column
    private double rate;

    @Column
    private int rateCount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public Product(ProductReq productReq) {
        this.name = productReq.getName();
        this.price = productReq.getPrice();
        this.category = productReq.getCategory();
    }

}
