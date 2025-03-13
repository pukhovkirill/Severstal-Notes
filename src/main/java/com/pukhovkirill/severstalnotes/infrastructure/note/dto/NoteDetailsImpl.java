package com.pukhovkirill.severstalnotes.infrastructure.note.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.entity.model.User;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;

@Getter
@Builder
@AllArgsConstructor
public class NoteDetailsImpl implements NoteDetails {

    private Long id;

    private User owner;

    private String title;

    private String content;

    private Timestamp createAt;

    private ImagePayload[] images;

    public NoteDetailsImpl(Note note){
        this.id = note.getId();
        this.owner = note.getOwner();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.createAt = note.getCreatedAt();
    }

}
