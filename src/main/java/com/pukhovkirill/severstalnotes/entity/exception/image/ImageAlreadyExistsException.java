package com.pukhovkirill.severstalnotes.entity.exception.image;

public class ImageAlreadyExistsException extends RuntimeException {

    public ImageAlreadyExistsException(String url) {
        super(String.format("Image '%s' already exists", url));
    }

    public ImageAlreadyExistsException(String url, Throwable cause) {
        super(String.format("Image '%s' already exists", url), cause);
    }

    public ImageAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
