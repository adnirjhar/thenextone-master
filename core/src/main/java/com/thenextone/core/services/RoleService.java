package com.thenextone.core.services;

import com.thenextone.core.entities.Role;
import com.thenextone.core.repositories.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private Environment environment;

    @Autowired
    private RolesRepository rolesRepository;

    public Role save(Role role) {
        return this.rolesRepository.save(role);
    }
}
