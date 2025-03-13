package com.pukhovkirill.severstalnotes.entity.gateway;

import java.util.List;
import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.exception.note.NoteAlreadyExistsException;
import com.pukhovkirill.severstalnotes.entity.exception.note.NoteNotFoundException;
import com.pukhovkirill.severstalnotes.entity.model.Note;

public interface NoteGateway {
    void create(Note note) throws NoteAlreadyExistsException;
    void update(Note note) throws NoteNotFoundException;
    void delete(Long noteId) throws NoteNotFoundException;

    Optional<Note> findByTitle(String title) throws NoteNotFoundException;

    List<Note> findAll();
}
