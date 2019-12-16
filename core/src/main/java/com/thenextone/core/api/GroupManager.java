package com.thenextone.core.api;

import com.thenextone.core.entities.Group;
import com.thenextone.core.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupManager {

    @Autowired
    public GroupService groupService;

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_FETCH_GROUP')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<Group>> fetchAll() {
        List<Group> groups = this.groupService.findAll();
        return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_ADD_GROUP')")
    @PostMapping(value = "/add")
    public ResponseEntity<Group> add(@RequestBody Group group) {
        return new ResponseEntity<Group>(this.groupService.save(group), HttpStatus.OK);
    }


}
