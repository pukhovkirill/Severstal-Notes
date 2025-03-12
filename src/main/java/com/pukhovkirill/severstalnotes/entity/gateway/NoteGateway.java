package com.pukhovkirill.severstalnotes.entity.gateway;

import java.util.List;
import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.model.Note;

public interface NoteGateway {
    void create(Note user);
    void update(Note user);
    void delete(Long userId);

    Optional<Note> findByEmail(String email);

    List<Note> findAll();
}
