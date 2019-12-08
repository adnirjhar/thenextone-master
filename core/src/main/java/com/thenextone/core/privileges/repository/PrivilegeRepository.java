package com.thenextone.core.privileges.repository;

import com.thenextone.core.privileges.dto.Privilege;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

    @Override
    public List<Privilege> findAll();
}
