package com.thenextone.core.groups.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thenextone.core.users.dto.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "UserGroups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnoreProperties("groups")
    private Set<User> users = new HashSet<>();

    public Group() {
    }

    public Group(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
