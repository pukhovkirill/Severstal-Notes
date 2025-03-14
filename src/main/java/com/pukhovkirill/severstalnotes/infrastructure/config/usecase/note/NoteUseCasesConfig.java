package com.pukhovkirill.severstalnotes.infrastructure.config.usecase.note;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.pukhovkirill.severstalnotes.usecase.note.*;
import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;

@Configuration
public class NoteUseCasesConfig {

    @Bean
    @Scope("prototype")
    public CreateNoteUseCase createNoteUseCase(NoteGateway noteGateway) {
        return new CreateNoteUseCase(noteGateway);
    }

    @Bean
    @Scope("prototype")
    public DeleteNoteUseCase deleteNoteUseCase(NoteGateway noteGateway) {
        return new DeleteNoteUseCase(noteGateway);
    }

    @Bean
    @Scope("prototype")
    public GetAllNotesUseCase getAllNotesUseCase(NoteGateway noteGateway) {
        return new GetAllNotesUseCase(noteGateway);
    }

    @Bean
    @Scope("prototype")
    public GetNoteUseCase getNoteUseCase(NoteGateway noteGateway) {
        return new GetNoteUseCase(noteGateway);
    }

    @Bean
    @Scope("prototype")
    public UpdateNoteUseCase updateNoteUseCase(NoteGateway noteGateway) {
        return new UpdateNoteUseCase(noteGateway);
    }
}
