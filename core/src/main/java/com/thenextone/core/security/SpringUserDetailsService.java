package com.thenextone.core.security;

import com.thenextone.core.entities.User;
import com.thenextone.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpringUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userService.findUserByEmail(s);
        return new SpringUser(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(), user.getGroups());
    }
}
