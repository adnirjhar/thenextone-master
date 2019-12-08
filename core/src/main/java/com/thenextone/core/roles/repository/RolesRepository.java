package com.thenextone.core.roles.repository;

import com.thenextone.core.roles.dto.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolesRepository extends CrudRepository<Role, Integer> {

    @Override
    public List<Role> findAll();
}
