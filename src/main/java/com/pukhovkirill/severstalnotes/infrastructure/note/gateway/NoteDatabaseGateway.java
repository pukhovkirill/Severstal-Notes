package com.pukhovkirill.severstalnotes.infrastructure.note.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.infrastructure.config.database.repository.NoteRepository;

@Service
@RequiredArgsConstructor
public class NoteDatabaseGateway implements NoteGateway {

    private final NoteRepository noteRepository;

    @Override
    public Note create(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note update(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void delete(Long noteId) {
        Optional<Note> note;
        if((note = noteRepository.findById(noteId)).isPresent()){
            noteRepository.delete(note.get());
        }
    }

    @Override
    public Optional<Note> findById(Long noteId) {
        return noteRepository.findById(noteId);
    }

    @Override
    public Optional<Note> findByTitle(String title) {
        return noteRepository.findByTitle(title);
    }

    @Override
    public List<Note> findAll() {
        final List<Note> notes = new ArrayList<>();
        noteRepository.findAll().forEach(notes::add);
        return notes;
    }
}
