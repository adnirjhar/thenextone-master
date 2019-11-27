package com.thenextone.app.usermanager;

import com.thenextone.app.models.AuthenticationRequest;
import com.thenextone.app.models.AuthenticationResponse;
import com.thenextone.core.users.dto.NewUser;
import com.thenextone.core.users.dto.User;
import com.thenextone.core.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserManager {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    private ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(this.userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    private ResponseEntity<User> getAllUsers(@RequestBody NewUser newUser) {
        return new ResponseEntity<User>(this.userService.addUser(newUser), HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate")
    private ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        final AuthenticationResponse res = new AuthenticationResponse(this.userService.authenticate(request.getUserName(),request.getPassword()));
        return new ResponseEntity<AuthenticationResponse>(res, HttpStatus.OK);
    }
}
