package com.thenextone.config.api;

import com.thenextone.config.entities.Config;
import com.thenextone.config.models.ConfigDTO;
import com.thenextone.config.services.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config")
public class ConfigManager {

    @Autowired
    private ConfigService configService;

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_FETCH_CONFIG')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<Config>> getAllConfigurations() {
        return new ResponseEntity<List<Config>>(this.configService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_ADD_CONFIG')")
    @PostMapping(value = "/add")
    public ResponseEntity<Config> addNewConfig(@RequestBody ConfigDTO config) {
        return new ResponseEntity<Config>(this.configService.add(config), HttpStatus.OK);
    }
}
