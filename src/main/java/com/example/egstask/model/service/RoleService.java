package com.example.egstask.model.service;

import com.example.egstask.exception.NotFoundException;
import com.example.egstask.model.entity.Role;
import com.example.egstask.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.egstask.common.ErrorMessages.NOT_FOUND;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(long id, String role) {
        roleRepository.save(new Role(id, role));
    }

    public Role findByRole(String roleName) {
        Optional<Role> role = roleRepository.findByRole(roleName);
        return role.orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void deleteAll() {
        roleRepository.deleteAll();
    }
}
