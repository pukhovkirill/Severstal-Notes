package com.pukhovkirill.severstalnotes.infrastructure.note.controller;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pukhovkirill.severstalnotes.entity.model.User;
import com.pukhovkirill.severstalnotes.infrastructure.imageResource.dto.ImagePayloadImpl;
import com.pukhovkirill.severstalnotes.infrastructure.imageResource.service.ImageResourceStorageService;
import com.pukhovkirill.severstalnotes.infrastructure.note.dto.NoteDetailsImpl;
import com.pukhovkirill.severstalnotes.infrastructure.note.service.NoteStorageService;
import com.pukhovkirill.severstalnotes.infrastructure.user.service.AuthorizedService;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final AuthorizedService authService;
    private final NoteStorageService noteStorageService;
    private final ImageResourceStorageService imageResourceStorageService;

    @GetMapping("/create")
    public String createNote(Model model){
        model.addAttribute("title", "Create note");
        return "edit";
    }

    @PostMapping("/save")
    public String saveNote(@RequestParam("noteTitle") String title, @RequestParam("htmlText") String htmlText, Model model) {

        try {
            String updatedHtml = processBase64Images(htmlText);

            NoteDetails noteDetails = NoteDetailsImpl.builder()
                            .title(title)
                            .content(updatedHtml)
                            .owner(getOwner())
                            .createAt(new Timestamp(System.currentTimeMillis()))
                            .build();

            noteStorageService.createNote(noteDetails);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/";
    }

    @GetMapping("/note")
    public String getNoteDetails(@RequestParam("id") Long nodeId, Model model){

        return "note";
    }

    @PostMapping("/update")
    public String updateNote(@RequestParam String htmlText, Model model) {
        model.addAttribute("title", "Edit note");
        return "redirect:/note?id=";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") Long nodeId, Model model){
        return "redirect:/";
    }

    private String processBase64Images(String htmlText) {
        Pattern pattern = Pattern.compile("data:image/([a-zA-Z]*);base64,([^\"]+)");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String extension = matcher.group(1);
            String base64Data = matcher.group(2);

            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            String filename = UUID.randomUUID() + "." + extension;

            String newImageUrl = createUrl(filename);

            ImagePayload payload = ImagePayloadImpl.builder()
                            .url(newImageUrl)
                            .name(filename)
                            .data(imageBytes)
                            .build();

            imageResourceStorageService.uploadImage(payload);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(newImageUrl));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    private String createUrl(String filename){
        var user = getOwner();
        return String.format("%s_%d/%s", user.getName(), user.getId(), filename);
    }

    private User getOwner(){
        var user = authService.findUserInContext();
        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found in context");
        return user.get();
    }

}
