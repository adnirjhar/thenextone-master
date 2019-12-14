package com.thenextone.core.services;

import com.thenextone.core.entities.User;
import com.thenextone.core.exceptions.GroupNotFoundException;
import com.thenextone.core.repositories.GroupRepository;
import com.thenextone.core.repositories.UserRepository;
import com.thenextone.core.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component("myUserService")
@Service
public class UserService {

    final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final User user = this.findUserByEmail(email);
            return jwtUtil.generateToken(user);
        }
        catch (BadCredentialsException | InternalAuthenticationServiceException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

    public List<User> fetchAllUsers() {
        return this.userRepository.findAll();
    }

    public User addUser(User user) throws Exception {
        if (user.getGroups() == null || user.getGroups().size() == 0) {
            throw new GroupNotFoundException("User needs to have a group");
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.getGroups().forEach(group -> this.groupRepository.save(group));
        return this.userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findFirstByEmail(email);
    }

    public boolean testtest(Authentication authentication, Object o, Object o1) {
        return false;
    }
}
