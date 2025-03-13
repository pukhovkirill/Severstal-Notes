package com.pukhovkirill.severstalnotes.infrastructure.note.service;

import java.util.List;

import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;

public interface NoteStorageService {

    void createNote(NoteDetails note);
    void updateNote(NoteDetails note);
    void deleteNote(NoteDetails note);
    NoteDetails getNote(NoteDetails note);
    List<NoteDetails> getAllNotes();

}
