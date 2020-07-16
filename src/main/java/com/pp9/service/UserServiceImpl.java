package com.pp9.service;

import com.pp9.model.Role;
import com.pp9.model.State;
import com.pp9.model.User;
import com.pp9.repositories.RoleRepository;
import com.pp9.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }

    private static Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public boolean registration(User user, String[] roles) {
        log.info("START registration");
        if (!usersRepository.existsUserByUsername(user.getUsername())) {
            String hashPassword = passwordEncoder.encode(user.getPassword());
            Set<Role> roleSet = Arrays.stream(roles).map(roleRepository::findByRole).collect(Collectors.toSet());
            user.setPassword(hashPassword);
            user.setRole(roleSet);
            user.setState(State.ACTIVE);
            log.info("START save USER");
            usersRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(User user, String[] roles) {
        log.info("START update");
        user.setRole(Arrays.stream(roles).map(roleRepository::findByRole).collect(Collectors.toSet()));
        usersRepository.save(user);
    }

    @Override
    public void delete(User user) {
        log.info("START delete");
        usersRepository.delete(user);
    }

    @Override
    public Optional<User> findByID(@javax.validation.constraints.NotNull Long id) {
        log.info("START find by id");
        return usersRepository.findById(id);
    }

    @Override
    public Optional<User> findOneByUsername(@NotNull String username) {
        log.info("START find by Login: " + username);
        Optional<User> user = usersRepository.findByUsername(username);
        log.info("USER IS PRESENT?: " + user.isPresent());
        return user;
    }

    @Override
    public List<User> findAll() {
        log.info("START findAll");
        List<User> userList = usersRepository.findAll();
        log.info("END findAll");
        return userList;
    }

    @Override
    public Set<String> findAllRole() {
        return roleRepository
                .findAll()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());
    }
}
