package com.thenextone.core.services;

import com.thenextone.core.entities.Privilege;
import com.thenextone.core.repositories.PrivilegeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    final Logger logger = LoggerFactory.getLogger(PrivilegeService.class);

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<Privilege> fetchAll() {
        return this.privilegeRepository.findAll();
    }

    public Privilege save(Privilege privilege) {
        return this.privilegeRepository.save(privilege);
    }

}
