package com.example.egstask.configuration.security;

import com.example.egstask.enums.RoleEnum;
import com.example.egstask.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RoleCreationConfiguration {

    private final RoleService roleService;

    @Autowired
    public RoleCreationConfiguration(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    void createAdminAccountTest() {
        if (roleService.findAll().size() != RoleEnum.values().length) {
            roleService.deleteAll();
            int index = 0;
            for (RoleEnum role : RoleEnum.values()) {
                roleService.save(++index, role.toString());
            }
        }

    }

}
