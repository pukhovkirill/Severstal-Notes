package com.pukhovkirill.severstalnotes.infrastructure.user.service;

import java.util.List;
import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.model.User;
import com.pukhovkirill.severstalnotes.infrastructure.user.dto.UserCredentials;


public interface UserService {

    User create(UserCredentials credentials);
    User update(UserCredentials credentials);
    void delete(UserCredentials credentials);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
