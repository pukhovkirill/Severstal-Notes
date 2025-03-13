package com.pukhovkirill.severstalnotes.infrastructure.user.service;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.entity.gateway.UserGateway;
import com.pukhovkirill.severstalnotes.entity.exception.user.UserNotFoundException;
import com.pukhovkirill.severstalnotes.entity.model.User;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserGateway userGateway;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userGateway.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email, new UsernameNotFoundException(String.format("User '%s' not found", email)))
        );

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
