package com.pukhovkirill.severstalnotes.usecase.image.dto;

import com.pukhovkirill.severstalnotes.entity.model.Note;

public interface ImagePayload {

    Long getId();

    Note getPlace();

    String getUrl();

    String getName();

    byte[] getData();

}
