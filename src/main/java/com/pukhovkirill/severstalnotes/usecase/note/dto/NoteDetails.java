package com.pukhovkirill.severstalnotes.usecase.note.dto;

import java.sql.Timestamp;

import com.pukhovkirill.severstalnotes.entity.model.User;

public interface NoteDetails {

    Long getId();

    User getOwner();

    String getTitle();

    String getContent();

    Timestamp getCreateAt();

}
