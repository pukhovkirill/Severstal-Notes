package com.pukhovkirill.severstalnotes.infrastructure.note.controller;

import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        model.addAttribute("formAction", "save");
        return "edit";
    }

    @PostMapping("/save")
    public String saveNote(
            @RequestParam("noteTitle") String title,
            @RequestParam("htmlText") String htmlText, Model model
    ) {

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
    public String getNoteDetails(@RequestParam("id") Long noteId, Model model){

        NoteDetails details = NoteDetailsImpl.builder()
                        .id(noteId)
                        .build();

        details = noteStorageService.getNote(details);

        model.addAttribute("noteId", details.getId());
        model.addAttribute("noteTitle", details.getTitle());
        model.addAttribute("noteContent", details.getContent());

        return "note";
    }

    @GetMapping("/edit")
    public String editNote(@RequestParam("id") Long noteId, Model model){
        NoteDetails details = NoteDetailsImpl.builder()
                .id(noteId)
                .build();

        details = noteStorageService.getNote(details);

        model.addAttribute("noteId", details.getId());
        model.addAttribute("noteTitle", details.getTitle());
        model.addAttribute("noteContent", details.getContent());
        model.addAttribute("title", "Edit note");
        model.addAttribute("formAction", "update");

        return "edit";
    }

    @PostMapping("/update")
    public String updateNote(
            @RequestParam("noteId") Long noteId,
            @RequestParam("noteTitle") String noteTitle,
            @RequestParam String htmlText, Model model
    ) {
        NoteDetails details = NoteDetailsImpl.builder()
                .id(noteId)
                .build();

        details = noteStorageService.getNote(details);
        deleteUnusedImages(details.getContent(), noteTitle);
        String updatedHtml = processBase64Images(htmlText);

        details = NoteDetailsImpl.builder()
                        .id(details.getId())
                        .title(noteTitle)
                        .owner(details.getOwner())
                        .content(updatedHtml).build();

        noteStorageService.updateNote(details);
        return "redirect:/note?id="+noteId;
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") Long noteId, Model model){
        NoteDetails details = NoteDetailsImpl.builder()
                .id(noteId)
                .build();

        details = noteStorageService.getNote(details);

        deleteAllImages(details.getContent());
        noteStorageService.deleteNote(details);

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
            matcher.appendReplacement(sb, Matcher.quoteReplacement("storage/"+newImageUrl));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    private void deleteAllImages(String htmlText) {
        List<String> sources = imageSources(htmlText);

        for(String source : sources) {
            String filename = Paths.get(source).getFileName().toString();
            ImagePayload payload = ImagePayloadImpl.builder()
                    .url(source)
                    .name(filename)
                    .build();

            imageResourceStorageService.deleteImage(payload);
        }
    }

    private void deleteUnusedImages(String originContent, String newContent){
        List<String> sources = imageSources(originContent);

        for(String source : sources) {
            if(!newContent.contains(source)){
                String filename = Paths.get(source).getFileName().toString();
                ImagePayload payload = ImagePayloadImpl.builder()
                        .url(source)
                        .name(filename)
                        .build();
                imageResourceStorageService.deleteImage(payload);
            }
        }
    }

    private List<String> imageSources(String htmlText){
        final List<String> srcs = new ArrayList<>();

        Pattern pattern = Pattern.compile("src=\"storage/([^/]+)/([^\"]+)\"");
        Matcher matcher = pattern.matcher(htmlText);

        while (matcher.find()) {
            String folder = matcher.group(1);
            String filename = matcher.group(2);
            srcs.add(folder+"/"+filename);
        }

        return srcs;
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
