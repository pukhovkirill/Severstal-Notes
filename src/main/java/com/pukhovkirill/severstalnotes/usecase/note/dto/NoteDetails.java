package com.pukhovkirill.severstalnotes.usecase.note.dto;

import java.sql.Timestamp;

import com.pukhovkirill.severstalnotes.entity.model.User;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;

public interface NoteDetails {

    Long getId();

    User getOwner();

    String getTitle();

    String getContent();

    Timestamp getCreateAt();

    ImagePayload[] getImages();

}
