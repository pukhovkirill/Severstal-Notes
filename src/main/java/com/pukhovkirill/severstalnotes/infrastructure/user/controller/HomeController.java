package com.pukhovkirill.severstalnotes.infrastructure.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pukhovkirill.severstalnotes.infrastructure.user.service.AuthorizedService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthorizedService authService;

    @GetMapping("/")
    public String welcome(){
        if(isAuthenticated())
            return "redirect:/home";
        return "welcome";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    private boolean isAuthenticated() {
        return authService.isAuthenticated();
    }

}
