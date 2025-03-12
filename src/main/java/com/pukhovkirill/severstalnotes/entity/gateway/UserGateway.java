package com.pukhovkirill.severstalnotes.entity.gateway;

import java.util.List;
import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.model.User;

public interface UserGateway {
    void create(User user);
    void update(User user);
    void delete(Long userId);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
