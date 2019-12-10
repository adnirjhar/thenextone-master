package com.thenextone.core.repositories;

import com.thenextone.core.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolesRepository extends CrudRepository<Role, Integer> {

    @Override
    public List<Role> findAll();

    @Override
    public Role save(Role role);
}
