package com.pukhovkirill.severstalnotes.entity.exception.note;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(String title) {
        super(String.format("Note '%s' not found", title));
    }

    public NoteNotFoundException(String title, Throwable cause) {
        super(String.format("Note '%s' not found", title));
    }

    public NoteNotFoundException(Throwable cause) {
        super(cause);
    }
}
