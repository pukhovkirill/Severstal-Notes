package com.pukhovkirill.severstalnotes.usecase.note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;
import com.pukhovkirill.severstalnotes.usecase.image.UploadImageUseCase;
import com.pukhovkirill.severstalnotes.entity.exception.note.NoteAlreadyExistsException;

@RequiredArgsConstructor
public class CreateNoteUseCase implements UseCase<List<Note>, NoteDetails> {

    private final NoteGateway noteGateway;
    private final UploadImageUseCase uploadImageUseCase;

    @Override
    public List<Note> execute(NoteDetails... args) {
        final List<Note> notes;

        notes = new ArrayList<>();
        if(args.length == 0)
            return notes;

        Note note;

        int idx = 0;
        try{
            for(; idx < args.length; idx++){
                var nt = args[idx];

                note = new Note();
                note.setTitle(nt.getTitle());
                note.setContent(nt.getContent());
                note.setCreatedAt(nt.getCreateAt());
                note.setOwner(nt.getOwner());

                uploadImageUseCase.execute(nt.getImages());
                noteGateway.create(note);

                notes.add(note);
            }
        }catch(NoteAlreadyExistsException e){
            var subarray = Arrays.copyOfRange(args, idx+1, args.length);
            notes.addAll(execute(subarray));
        }

        return notes;
    }

}
