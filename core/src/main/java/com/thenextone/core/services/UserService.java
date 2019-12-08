package com.thenextone.core.services;

import com.thenextone.core.exceptions.GroupNotFoundException;
import com.thenextone.core.groups.dto.Group;
import com.thenextone.core.groups.repository.GroupRepository;
import com.thenextone.core.users.dto.User;
import com.thenextone.core.users.repository.UserRepository;
import com.thenextone.core.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private Environment environment;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public String authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final User user = userRepository.findUserByEmail(email);
            return jwtUtil.generateToken(user);
        }
        catch (BadCredentialsException | InternalAuthenticationServiceException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }

    }

    @EventListener(ApplicationReadyEvent.class)
    private void loadUserOnStart() throws Exception {
        final String masterEmail = environment.getProperty("master.admin.email");
        final String masterPassword = environment.getProperty("master.admin.password");
        User admin = userRepository.findFirstByEmail(masterEmail);

        if (admin == null) {
            Set<Group> newAdminGroup = new HashSet<Group>(Arrays.asList(new Group("Admin Group", "ADMIN")));
            User user = new User("Super","Admin", masterEmail, masterPassword, newAdminGroup);
            this.addUser(user);

            logger.info("============================ Admin User Added - " + masterEmail + " ==========================");
        }
    }

}
