package com.pukhovkirill.severstalnotes.infrastructure.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pukhovkirill.severstalnotes.infrastructure.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String welcome(){
        if(isAuthenticated())
            return "redirect:/home";
        return "welcome";
    }

    private boolean isAuthenticated() {
        return userService.isAuthenticated();
    }

}
