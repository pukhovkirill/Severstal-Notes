package com.pukhovkirill.severstalnotes.infrastructure.note.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.usecase.note.*;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;
import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.infrastructure.note.dto.NoteDetailsImpl;
import com.pukhovkirill.severstalnotes.infrastructure.imageResource.service.ImageResourceStorageService;

@Service
@RequiredArgsConstructor
public class NoteStorageServiceImpl implements NoteStorageService {

    private final BeanFactory beanFactory;
    private final ImageResourceStorageService imageResourceStorage;

    @Override
    public void createNote(NoteDetails note) {
        try{
            var createNoteUseCase = (CreateNoteUseCase) beanFactory.getBean(
                    "createNoteUseCase",
                    CreateNoteUseCase.class
            );

            createNoteUseCase.execute(note);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateNote(NoteDetails note) {
        try{
            var updateNoteUseCase = (UpdateNoteUseCase) beanFactory.getBean(
                    "updateNoteUseCase",
                    UpdateNoteUseCase.class
            );

            updateNoteUseCase.execute(note);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteNote(NoteDetails note) {
        try{
            var deleteNoteUseCase = (DeleteNoteUseCase) beanFactory.getBean(
                    "deleteNoteUseCase",
                    DeleteNoteUseCase.class
            );

            deleteNoteUseCase.execute(note);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public NoteDetails getNote(NoteDetails note) {
        NoteDetailsImpl noteDetails = null;
        try{
            var getNoteUseCase = (GetNoteUseCase) beanFactory.getBean(
                    "getNoteUseCase",
                    GetNoteUseCase.class
            );

            List<Note> eNote = getNoteUseCase.execute(note);
            if(eNote.size() == 1){
                noteDetails = new NoteDetailsImpl(eNote.get(0));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return noteDetails;
    }

    @Override
    public List<NoteDetails> getAllNotes() {
        try{
            var getAllNotesUseCase = (GetAllNotesUseCase) beanFactory.getBean(
                    "getAllNotesUseCase",
                    GetAllNotesUseCase.class
            );

            List<Note> notes = getAllNotesUseCase.execute();
            return notes.stream()
                    .map(NoteDetailsImpl::new)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
