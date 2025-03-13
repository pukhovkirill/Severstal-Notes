package com.pukhovkirill.severstalnotes.entity.gateway;

import java.util.List;
import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.exception.user.UserAlreadyExistsException;
import com.pukhovkirill.severstalnotes.entity.exception.user.UserNotFoundException;
import com.pukhovkirill.severstalnotes.entity.model.User;

public interface UserGateway {
    User create(User user) throws UserAlreadyExistsException;
    User update(User user) throws UserNotFoundException;
    void delete(Long userId) throws UserNotFoundException;

    Optional<User> findByEmail(String email) throws UserNotFoundException;

    List<User> findAll();
}
