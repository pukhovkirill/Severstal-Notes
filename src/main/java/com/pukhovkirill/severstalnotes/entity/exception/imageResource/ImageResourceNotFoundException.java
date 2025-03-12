package com.pukhovkirill.severstalnotes.entity.exception.imageResource;

public class ImageResourceNotFoundException extends RuntimeException {
    public ImageResourceNotFoundException(String src) {
        super(String.format("Image resource '%s' not found", src));
    }

    public ImageResourceNotFoundException(String src, Throwable cause) {
        super(String.format("Image resource '%s' not found", src), cause);
    }

    public ImageResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
