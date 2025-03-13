package com.pukhovkirill.severstalnotes.infrastructure.user.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.entity.gateway.UserGateway;
import com.pukhovkirill.severstalnotes.entity.model.User;
import com.pukhovkirill.severstalnotes.infrastructure.config.database.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDatabaseGateway implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        Optional<User> user;
        if((user = userRepository.findById(userId)).isPresent()){
            userRepository.delete(user.get());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
