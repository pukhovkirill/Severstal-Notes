package com.pukhovkirill.severstalnotes.infrastructure.user.service;

import com.pukhovkirill.severstalnotes.entity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findPerson();
    boolean isAuthenticated();

}
