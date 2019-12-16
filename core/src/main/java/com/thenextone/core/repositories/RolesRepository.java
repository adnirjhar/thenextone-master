package com.thenextone.core.repositories;

import com.thenextone.core.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RolesRepository extends CrudRepository<Role, Integer> {

    @Override
    public List<Role> findAll();

    @Override
    Optional<Role> findById(Integer id);

    @Override
    public Role save(Role role);
}
