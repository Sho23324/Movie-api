package com.example.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_api.model.Role;
import com.example.movie_api.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Role getRole(Long id) {
        return roleRepository.findById(id).get();
    }

    public void updateRole(Long id, Role updatedRole) {
        Role role = getRole(id);
        updatedRole.setId(role.getId());
        roleRepository.save(updatedRole);

    }
}
