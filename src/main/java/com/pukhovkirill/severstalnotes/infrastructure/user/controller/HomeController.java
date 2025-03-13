package com.pukhovkirill.severstalnotes.infrastructure.user.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pukhovkirill.severstalnotes.infrastructure.user.service.AuthorizedService;
import com.pukhovkirill.severstalnotes.infrastructure.note.service.NoteStorageService;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthorizedService authService;
    private final NoteStorageService noteStorageService;

    @GetMapping("/")
    public String welcome(){
        if(isAuthenticated())
            return "redirect:/home";
        return "welcome";
    }

    @GetMapping("/home")
    public String home(Model model){
        List<NoteDetails> notes = noteStorageService.getAllNotes();
        model.addAttribute("notes", notes);
        return "home";
    }

    private boolean isAuthenticated() {
        return authService.isAuthenticated();
    }

}
