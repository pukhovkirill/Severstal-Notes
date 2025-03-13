package com.pukhovkirill.severstalnotes.infrastructure.config.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pukhovkirill.severstalnotes.entity.model.Note;

public interface NoteRepository extends CrudRepository<Note, Long> {
    Optional<Note> findByTitle(String title);
}
