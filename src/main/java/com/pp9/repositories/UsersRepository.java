package com.pp9.repositories;

import com.pp9.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    @Override
    <S extends User> S save(S s);

    @Override
    void delete(User user);

    boolean existsUserByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();
}
