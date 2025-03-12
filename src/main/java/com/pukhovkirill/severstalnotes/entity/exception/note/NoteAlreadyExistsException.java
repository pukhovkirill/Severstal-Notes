package com.pukhovkirill.severstalnotes.entity.exception.note;

public class NoteAlreadyExistsException extends RuntimeException {

    public NoteAlreadyExistsException(String title) {
        super(String.format("Note '%s' already exists", title));
    }

    public NoteAlreadyExistsException(String title, Throwable cause) {
        super(String.format("Note '%s' already exists", title), cause);
    }

    public NoteAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
