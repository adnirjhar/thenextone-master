package com.thenextone.core.repositories;

import com.thenextone.core.entities.Privilege;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

    @Override
    public List<Privilege> findAll();

    @Override
    public Privilege save(Privilege privilege);
}
