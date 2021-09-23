package com.example.egstask.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    private Long id;

    private String role;

    public Role (Long id, String role) {
        this.id = id;
        this.role = role;
    }

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "egs_user_id", referencedColumnName = "id"))
    private Set<EgsUser> users;

}
