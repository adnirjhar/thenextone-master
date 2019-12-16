package com.thenextone.core.api;

import com.thenextone.core.entities.User;
import com.thenextone.core.models.AuthenticationRequest;
import com.thenextone.core.models.AuthenticationResponse;
import com.thenextone.core.models.UserDTO;
import com.thenextone.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserManager {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        final AuthenticationResponse res = new AuthenticationResponse(this.userService.authenticate(request.getUserName(),request.getPassword()));
        return new ResponseEntity<AuthenticationResponse>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_FETCH_USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(this.userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('PRIVILEGE','PRIVILEGE_ADD_USER')")
    @PostMapping(value = "/add")
    public ResponseEntity<User> addUser(@RequestBody UserDTO user) throws Exception {
        return new ResponseEntity<User>(this.userService.addUser(user), HttpStatus.OK);
    }
}
