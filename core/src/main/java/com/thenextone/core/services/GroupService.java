package com.thenextone.core.services;

import com.thenextone.core.entities.Group;
import com.thenextone.core.repositories.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private Environment environment;

    @Autowired
    private GroupRepository groupRepository;

    public Group save(Group group) {
        return this.groupRepository.save(group);
    }

    public List<Group> findAll() {
        return this.groupRepository.findAll();
    }

}
