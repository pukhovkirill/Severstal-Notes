package com.pukhovkirill.severstalnotes.usecase.note;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.model.Note;
import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.note.dto.NoteDetails;
import com.pukhovkirill.severstalnotes.entity.exception.note.NoteNotFoundException;

@RequiredArgsConstructor
public class DeleteNoteUseCase implements UseCase<NoteDetails, NoteDetails> {

    private final NoteGateway noteGateway;

    @Override
    public NoteDetails execute(NoteDetails... args) {
        if(args == null || args.length == 0)
            return null;

        Note note;

        int idx = 0;
        try{
            for(; idx < args.length; idx++){
                var nt = args[idx];

                note = noteGateway.findByTitle(nt.getTitle())
                        .orElseThrow(() -> new NoteNotFoundException(nt.getTitle()));

                noteGateway.delete(note.getId());
            }
        }catch(NoteNotFoundException e){
            var subarray = Arrays.copyOfRange(args, idx+1, args.length);
            return execute(subarray);
        }

        return args[args.length-1];
    }
}