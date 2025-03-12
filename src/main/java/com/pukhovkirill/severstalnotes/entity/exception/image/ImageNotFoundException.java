package com.pukhovkirill.severstalnotes.entity.exception.image;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String url) {
        super(String.format("Image '%s' not found", url));
    }

    public ImageNotFoundException(String url, Throwable cause) {
        super(String.format("Image '%s' not found", url), cause);
    }

    public ImageNotFoundException(Throwable cause) {
        super(cause);
    }
}
