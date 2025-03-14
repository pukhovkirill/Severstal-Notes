package com.pukhovkirill.severstalnotes.usecase.note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;
import com.pukhovkirill.severstalnotes.entity.exception.note.NoteNotFoundException;

@RequiredArgsConstructor
public class GetNoteUseCase implements UseCase<List<Note>, NoteDetails> {

    private final NoteGateway noteGateway;

    @Override
    public List<Note> execute(NoteDetails... args) {
        final List<Note> notes;

        notes = new ArrayList<>();
        if(args == null || args.length == 0)
            return notes;

        Note note;

        int idx = 0;
        try{
            for(; idx < args.length; idx++){
                var nt = args[idx];

                note = noteGateway.findById(nt.getId())
                        .orElseThrow(() -> new NoteNotFoundException(nt.getId().toString()));
                notes.add(note);
            }
        }catch(NoteNotFoundException e){
            var subarray = Arrays.copyOfRange(args, idx+1, args.length);
            notes.addAll(execute(subarray));
        }

        return notes;
    }
}
