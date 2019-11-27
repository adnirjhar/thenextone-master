package com.thenextone.core.users.services;

import com.thenextone.core.users.dto.NewUser;
import com.thenextone.core.users.dto.User;
import com.thenextone.core.users.repository.UserRepository;
import com.thenextone.core.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private
    PasswordEncoder passwordEncoder;

    public List<User> fetchAllUsers() {
        return this.userRepository.findAll();
    }

    public User addUser(NewUser user) {
        String encoded = this.passwordEncoder.encode(user.getPassword());
        User newUser = new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encoded
        );
        return this.userRepository.save(newUser);
    }

    public String authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }
        catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password");
        }
        final User user = userRepository.findUserByEmail(email);
        return jwtUtil.generateToken(user);
    }

}
