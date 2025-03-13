package com.pukhovkirill.severstalnotes.infrastructure.note.controller;

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
public class NoteController {

    @GetMapping("/create")
    public String createNote(){
        return "edit";
    }

    @PostMapping("/save")
    public String saveNote(@RequestParam String htmlText, Model model) {
        try {
            // Обрабатываем текст и заменяем Base64-изображения на файлы
            String updatedHtml = processBase64Images(htmlText);

            // Здесь можно сохранить updatedHtml в БД
            System.out.println("Сохранённый HTML: " + updatedHtml);

            model.addAttribute("message", "Текст сохранён!");
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка при сохранении.");
        }
        return "home";
    }

    @GetMapping("/note")
    public String getNoteDetails(@RequestParam("id") Long nodeId, Model model){
        return "note";
    }

    @PostMapping("/update")
    public String updateNote(@RequestParam String htmlText, Model model) {
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
