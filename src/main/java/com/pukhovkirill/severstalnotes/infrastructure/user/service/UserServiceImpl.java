package com.pukhovkirill.severstalnotes.infrastructure.user.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.infrastructure.user.dto.UserCredentials;
import com.pukhovkirill.severstalnotes.entity.gateway.UserGateway;
import com.pukhovkirill.severstalnotes.entity.model.User;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(UserCredentials credentials) {
        var user = new User();
        user.setEmail(credentials.getEmail());
        user.setName(credentials.getName());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        return userGateway.create(user);
    }

    @Override
    public User update(UserCredentials credentials) {
        var user = new User();
        user.setEmail(credentials.getEmail());
        user.setName(credentials.getName());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        return userGateway.update(user);
    }

    @Override
    public void delete(UserCredentials credentials) {
        var user = userGateway.findByEmail(credentials.getEmail());
        userGateway.delete(user.get().getId());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userGateway.findAll();
    }
}
