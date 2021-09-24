package com.example.egstask.model.entity;

import com.example.egstask.model.dto.response.TokenRes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user",
        indexes = {@Index(name = "username_index", columnList = "username")})
public class EgsUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private long id;

    @Column
    @JsonIgnore
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    private Boolean locked = false;

    @Column
    private Date created;

    @Column
    private Date updated;

    @Transient
    private TokenRes token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "egs_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    private Set<Role> roles;

}
