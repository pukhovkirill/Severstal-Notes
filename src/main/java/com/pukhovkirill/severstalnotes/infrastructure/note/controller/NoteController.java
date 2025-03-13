package com.pukhovkirill.severstalnotes.infrastructure.note.controller;

import com.pukhovkirill.severstalnotes.infrastructure.note.service.NoteStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteStorageService noteStorageService;

    @GetMapping("/create")
    public String createNote(Model model){
        model.addAttribute("title", "Create note");
        return "edit";
    }

    @PostMapping("/save")
    public String saveNote(@RequestParam String htmlText, Model model) {
        try {
            String updatedHtml = processBase64Images(htmlText);

        } catch (Exception e) {

        }
        return "home";
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

    private String processBase64Images(String htmlText) throws IOException {
        Pattern pattern = Pattern.compile("data:image/([a-zA-Z]*);base64,([^\"]+)");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String extension = matcher.group(1);
            String base64Data = matcher.group(2);

            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            String filename = UUID.randomUUID() + "." + extension;

            File uploadDir = new File("/home/yukir/");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            Path filePath = Paths.get("/home/yukir/", filename);
            Files.write(filePath, imageBytes);

            String newImageUrl = "/uploads/" + filename;
            matcher.appendReplacement(sb, Matcher.quoteReplacement(newImageUrl));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
