package com.example.egstask.model.dto.response;

import com.example.egstask.model.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRes {

    private long id;
    private String name;

    public CategoryRes(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

}
