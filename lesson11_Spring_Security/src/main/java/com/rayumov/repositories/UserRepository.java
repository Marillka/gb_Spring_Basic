package com.rayumov.repositories;

import com.rayumov.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    //    Optional<User> findByUsername(String username);
    Optional<User> findByUsername(String username);
}
