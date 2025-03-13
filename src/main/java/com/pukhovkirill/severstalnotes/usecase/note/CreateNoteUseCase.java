package com.pukhovkirill.severstalnotes.usecase.note;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;
import com.pukhovkirill.severstalnotes.usecase.image.UploadImageUseCase;
import com.pukhovkirill.severstalnotes.entity.model.Image;

@RequiredArgsConstructor
public class CreateNoteUseCase implements UseCase<List<Note>, NoteDetails> {

    private final NoteGateway noteGateway;
    private final UploadImageUseCase uploadImageUseCase;

    @Override
    public List<Note> execute(NoteDetails... args) {
        if(args.length == 0)
            return null;

        final List<Note> notes;

        Note note;
        notes = new ArrayList<>();
        for(var nt : args){
            note = new Note();
            note.setTitle(nt.getTitle());
            note.setContent(nt.getContent());
            note.setCreatedAt(nt.getCreateAt());
            note.setOwner(nt.getOwner());

            List<Image> images = uploadImageUseCase.execute(nt.getImages());

            note.addImage(images.toArray(new Image[0]));

            notes.add(note);
        }

        return notes;
    }

}
