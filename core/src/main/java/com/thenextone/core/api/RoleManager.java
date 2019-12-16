package com.thenextone.core.api;

import com.thenextone.core.entities.Role;
import com.thenextone.core.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleManager {

    @Autowired
    public RoleService roleService;

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_FETCH_ROLE')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<Role>> fetchAll() {
        List<Role> roles = this.roleService.getAllRoles();
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_ADD_ROLE')")
    @PostMapping(value = "/add")
    public ResponseEntity<Role> add(@RequestBody Role role) {
        return new ResponseEntity<Role>(this.roleService.save(role), HttpStatus.OK);
    }


}
