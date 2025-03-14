package com.pukhovkirill.severstalnotes.infrastructure.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pukhovkirill.severstalnotes.entity.model.User;
import com.pukhovkirill.severstalnotes.infrastructure.user.service.UserService;
import com.pukhovkirill.severstalnotes.infrastructure.user.dto.UserCredentials;
import com.pukhovkirill.severstalnotes.infrastructure.user.service.AuthorizedService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthorizedService authService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new UserCredentials());
        return "register";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid UserCredentials credentials,
                               Errors errors,
                               Model model){
        var existingPerson = userService.findByEmail(credentials.getEmail());

        if(errors.hasErrors()){
            model.addAttribute("user", credentials);
            return "register";
        }

        if(existingPerson.isPresent()){
            errors.rejectValue("email", null,
                    "There is already an account registered with the same email");
            return "register";
        }

        userService.create(credentials);
        return "redirect:/login";
    }

    @GetMapping("/removeAccount")
    public String removeAccount(){
        var contextUser = authService.findUserInContext();
        if(contextUser.isEmpty())
            throw new UsernameNotFoundException("User not found in context");

        User user = contextUser.get();
        userService.delete(new UserCredentials(user));

        return "redirect:/logout";
    }

}
