package com.thenextone.core.api;

import com.thenextone.core.entities.Group;
import com.thenextone.core.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupManager {

    @Autowired
    public GroupService groupService;

    @PreAuthorize("hasPermission('GROUP','ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<Group>> getAllUsers() {
        List<Group> groups = this.groupService.findAll();
        return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('GROUP','ADMIN')")
    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<String>("Test test test", HttpStatus.OK);
    }
}
