package com.thenextone.core.repositories;

import com.thenextone.core.entities.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {

    @Override
    public List<Group> findAll();

    @Override
    public Group save(Group newGroup);

    public Group findFirstByCode(String code);
}
