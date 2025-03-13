package com.pukhovkirill.severstalnotes.infrastructure.user.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.entity.exception.user.UserNotFoundException;
import com.pukhovkirill.severstalnotes.entity.gateway.UserGateway;
import com.pukhovkirill.severstalnotes.entity.model.User;

@Service
@AllArgsConstructor
public class AuthorizedServiceImpl implements AuthorizedService {

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

    @Override
    public Optional<User> findUserInContext() {
        var details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(details instanceof UserDetails user)
            return Optional.of(
                    userGateway.findByEmail(
                            user.getUsername()
                    ).get()
            );

        return Optional.empty();
    }

    @Override
    public boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
