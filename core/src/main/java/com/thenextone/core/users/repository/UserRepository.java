package com.thenextone.core.users.repository;

import com.thenextone.core.users.dto.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    public List<User> findAll();

    @Override
    public User save(User user);

    public User findUserByEmail(String email);

}
