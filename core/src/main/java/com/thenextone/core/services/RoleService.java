package com.thenextone.core.services;

import com.thenextone.core.entities.Role;
import com.thenextone.core.repositories.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RolesRepository rolesRepository;

    public List<Role> getAllRoles() {
        return this.rolesRepository.findAll();
    }

    public Role save(Role role) {
        return this.rolesRepository.save(role);
    }
}
