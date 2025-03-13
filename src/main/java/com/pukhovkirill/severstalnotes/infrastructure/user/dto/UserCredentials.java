package com.pukhovkirill.severstalnotes.infrastructure.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.pukhovkirill.severstalnotes.entity.model.User;
import com.pukhovkirill.severstalnotes.infrastructure.user.validation.email.ValidEmail;
import com.pukhovkirill.severstalnotes.infrastructure.user.validation.password.ValidPassword;

@Getter
@Builder
@AllArgsConstructor
public class UserCredentials{

    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @NotBlank
    @ValidPassword
    private String password;

    public UserCredentials(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
    }
}