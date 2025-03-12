package com.pukhovkirill.severstalnotes.entity.exception.imageResource;

public class ImageResourceAlreadyExistsException extends RuntimeException {
    public ImageResourceAlreadyExistsException(String src) {
        super(String.format("Image resource '%s' already exists", src));
    }

    public ImageResourceAlreadyExistsException(String src, Throwable cause) {
        super(String.format("Image resource '%s' already exists", src), cause);
    }

    public ImageResourceAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
