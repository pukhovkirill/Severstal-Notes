package com.pukhovkirill.severstalnotes.usecase.note;

import java.sql.Timestamp;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;
import com.pukhovkirill.severstalnotes.entity.model.Image;
import com.pukhovkirill.severstalnotes.usecase.image.UploadImageUseCase;
import com.pukhovkirill.severstalnotes.entity.exception.note.NoteNotFoundException;

@RequiredArgsConstructor
public class UpdateNoteUseCase implements UseCase<Note, NoteDetails> {

    private final NoteGateway noteGateway;
    private final UploadImageUseCase uploadImageUseCase;

    @Override
    public Note execute(NoteDetails... args) {
        if(args.length != 1)
            return null;

        Note note;

        var nt = args[0];

        note = noteGateway.findById(nt.getId())
                .orElseThrow(() -> new NoteNotFoundException(args[0].getTitle()));

        if(doChanges(note, nt)){
            List<Image> images = uploadImageUseCase.execute(nt.getImages());
            note.addImage(images.toArray(new Image[0]));

            noteGateway.update(note);
        }

        return note;
    }

    private boolean doChanges(Note note, NoteDetails noteDetails){
        boolean isChanged = false;

        if(!note.getTitle().equals(noteDetails.getTitle())){
            note.setTitle(noteDetails.getTitle());
            isChanged = true;
        }

        if(!note.getContent().equals(noteDetails.getContent())){
            note.setContent(noteDetails.getContent());
            isChanged = true;
        }

        if(isChanged){
            note.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }

        return isChanged;
    }
}
