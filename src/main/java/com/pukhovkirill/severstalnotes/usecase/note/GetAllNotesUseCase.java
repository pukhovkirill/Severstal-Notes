package com.pukhovkirill.severstalnotes.usecase.note;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.usecase.UseCase;

@RequiredArgsConstructor
public class GetAllNotesUseCase implements UseCase<List<Note>, Void> {

    private final NoteGateway noteGateway;

    @Override
    public List<Note> execute(Void... args) {
        final List<Note> notes;
        notes = noteGateway.findAll();
        return notes;
    }

}
