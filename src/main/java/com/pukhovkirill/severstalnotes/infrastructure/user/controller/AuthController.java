package com.pukhovkirill.severstalnotes.infrastructure.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pukhovkirill.severstalnotes.infrastructure.user.service.UserService;
import com.pukhovkirill.severstalnotes.infrastructure.user.dto.UserCredentials;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

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

}
