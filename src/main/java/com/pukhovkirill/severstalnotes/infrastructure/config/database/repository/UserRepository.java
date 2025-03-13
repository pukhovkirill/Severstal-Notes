package com.pukhovkirill.severstalnotes.infrastructure.config.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pukhovkirill.severstalnotes.entity.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
