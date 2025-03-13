package com.pukhovkirill.severstalnotes.infrastructure.imageResource.service;

import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;

public interface ImageResourceStorageService {

    void uploadImage(ImagePayload image);
    ImagePayload getImage(ImagePayload image);
    void deleteImage(ImagePayload image);

}
