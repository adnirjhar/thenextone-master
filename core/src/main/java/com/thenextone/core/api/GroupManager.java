package com.thenextone.core.api;

import com.thenextone.core.exceptions.GroupNotFoundException;
import com.thenextone.core.groups.dto.Group;
import com.thenextone.core.groups.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupManager {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping(value = "/all")
    private ResponseEntity<List<Group>> getAllUsers() {
        return new ResponseEntity<List<Group>>(this.groupRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    private ResponseEntity<List<Group>> test() {
        throw new GroupNotFoundException("No group found");
    }
}
