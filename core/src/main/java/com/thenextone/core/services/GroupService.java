package com.thenextone.core.services;

import com.thenextone.core.entities.Group;
import com.thenextone.core.entities.Role;
import com.thenextone.core.exceptions.BadRequestException;
import com.thenextone.core.repositories.GroupRepository;
import com.thenextone.core.repositories.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public Optional<Group> findById(Integer id) {
        return this.groupRepository.findById(id);
    }

    public Group save(Group group) {
        List<Role> roles = new ArrayList<>();

        group.getRoles().forEach(role -> {
            if (role.getId() == null) throw new BadRequestException("Invalid role id");

            roles.add(this.rolesRepository.findById(role.getId()).orElseThrow(() -> {
                return new BadRequestException("Cannot assign role. Role not found.");
            }));
        });
        group.setRoles(roles);

        return this.groupRepository.save(group);
    }

    public List<Group> findAll() {
        return this.groupRepository.findAll();
    }

}
