package com.pukhovkirill.severstalnotes.infrastructure.config.usecase.note;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.pukhovkirill.severstalnotes.entity.gateway.NoteGateway;
import com.pukhovkirill.severstalnotes.usecase.image.DeleteImageUseCase;
import com.pukhovkirill.severstalnotes.usecase.image.UploadImageUseCase;
import com.pukhovkirill.severstalnotes.usecase.note.CreateNoteUseCase;
import com.pukhovkirill.severstalnotes.usecase.note.DeleteNoteUseCase;
import com.pukhovkirill.severstalnotes.usecase.note.GetNoteUseCase;
import com.pukhovkirill.severstalnotes.usecase.note.UpdateNoteUseCase;

@Configuration
public class NoteUseCasesConfig {

    @Bean
    @Scope("prototype")
    public CreateNoteUseCase createNoteUseCase(
            NoteGateway noteGateway,
            UploadImageUseCase uploadImageUseCase
    ) {
        return new CreateNoteUseCase(noteGateway, uploadImageUseCase);
    }

    @Bean
    @Scope("prototype")
    public DeleteNoteUseCase deleteImageUseCase(
            NoteGateway noteGateway,
            DeleteImageUseCase deleteImageUseCase
    ) {
        return new DeleteNoteUseCase(noteGateway, deleteImageUseCase);
    }

    @Bean
    @Scope("prototype")
    public GetNoteUseCase getNoteUseCase(NoteGateway noteGateway) {
        return new GetNoteUseCase(noteGateway);
    }

    @Bean
    @Scope("prototype")
    public UpdateNoteUseCase updateNoteUseCase(
            NoteGateway noteGateway,
            UploadImageUseCase uploadImageUseCase
    ) {
        return new UpdateNoteUseCase(noteGateway, uploadImageUseCase);
    }
}
