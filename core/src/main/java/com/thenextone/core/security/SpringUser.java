package com.thenextone.core.security;

import com.thenextone.core.entities.Group;
import com.thenextone.core.entities.Privilege;
import com.thenextone.core.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpringUser implements UserDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Group> groups;
    private List<Role> roles;
    private List<Privilege> privileges;

    public SpringUser() {

    }

    public SpringUser(
            String firstName,
            String lastName,
            String email,
            String password,
            List<Group> groups) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.groups = groups;
        this.roles = this.getRoles();
        this.privileges = this.getPrivileges();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.groups.forEach(group -> {
            group.getRoles().forEach(role -> {
                role.getPrivileges().forEach(privilege -> {
                    new SimpleGrantedAuthority(privilege.getCode());
                });
            });
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        this.groups.forEach(group -> {
            roles.addAll(group.getRoles());
        });
        return roles;
    }

    public List<Privilege> getPrivileges() {
        List<Privilege> privileges = new ArrayList<>();
        this.groups.forEach(group -> {
            group.getRoles().forEach(role -> {
                privileges.addAll(role.getPrivileges());
            });
        });
        return privileges;
    }
}
