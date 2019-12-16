package com.thenextone.core.services;

import com.thenextone.core.entities.Group;
import com.thenextone.core.entities.User;
import com.thenextone.core.exceptions.BadRequestException;
import com.thenextone.core.exceptions.GroupNotFoundException;
import com.thenextone.core.models.UserDTO;
import com.thenextone.core.repositories.UserRepository;
import com.thenextone.core.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("myUserService")
@Service
public class UserService {

    final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

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

    public User addUser(UserDTO user) throws Exception {
        User userByEmail = this.userRepository.findUserByEmail(user.getEmail());
        if (userByEmail != null) {
            throw new BadRequestException("User with same email already exists.");
        }

        if (user.getGroups() == null || user.getGroups().size() == 0) {
            throw new GroupNotFoundException("User needs to have atleast one group");
        }
        else {
            List<Group> groups = new ArrayList<>();
            user.getGroups().forEach(group -> {
                if (group.getId() == null) throw new BadRequestException("User needs to have a valid group");
                Optional<Group> groupOptional = Optional.ofNullable(
                        this.groupService.findById(group.getId()).orElseThrow(() -> {
                            return new BadRequestException("Group doesnt exist");
                        }));

                groups.add(groupOptional.get());
            });
            user.setGroups(groups);
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        User newUser = new User(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getGroups());
        return this.userRepository.save(newUser);
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findFirstByEmail(email);
    }
}
