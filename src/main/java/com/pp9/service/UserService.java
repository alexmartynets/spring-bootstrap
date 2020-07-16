package com.pp9.service;

import com.pp9.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    boolean registration(User user, String[] roles);

    void update(User user, String[] roles);

//    void saveOrUpdate(User user, String adminRole, String userRole) throws NotFoundException;

    void delete(User user);

    Optional<User> findByID(Long id);

    Optional<User> findOneByUsername(String username);

    List<User> findAll();

    Set<String> findAllRole();
}