package com.example.egstask.model.dto.response;

import com.example.egstask.model.entity.EgsUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRes {

    private long id;
    private String username;
    private Date created;
    private Date updated;

    public UserRes(EgsUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.created = user.getCreated();
        this.updated = user.getUpdated();
    }

}
