package com.thenextone.core.api;

import com.thenextone.core.entities.Privilege;
import com.thenextone.core.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privileges")
public class PrivilegeManager {

    @Autowired
    public PrivilegeService privilegeService;

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_FETCH_PRIVILEGE')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<Privilege>> fetchAll() {
        List<Privilege> privileges = this.privilegeService.fetchAll();
        return new ResponseEntity<List<Privilege>>(privileges, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_ADD_PRIVILEGE')")
    @PostMapping(value = "/add")
    public ResponseEntity<Privilege> add(@RequestBody Privilege privilege) {
        return new ResponseEntity<Privilege>(this.privilegeService.save(privilege), HttpStatus.OK);
    }


}
