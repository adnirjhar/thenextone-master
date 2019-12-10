package com.thenextone.core.services;

import com.thenextone.core.entities.Privilege;
import com.thenextone.core.repositories.PrivilegeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {

    final Logger logger = LoggerFactory.getLogger(PrivilegeService.class);

    @Autowired
    private Environment environment;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public Privilege save(Privilege privilege) {
        return this.privilegeRepository.save(privilege);
    }

}
