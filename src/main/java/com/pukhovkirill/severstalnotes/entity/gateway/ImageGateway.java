package com.pukhovkirill.severstalnotes.entity.gateway;

import java.util.List;
import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.exception.image.ImageAlreadyExistsException;
import com.pukhovkirill.severstalnotes.entity.exception.image.ImageNotFoundException;
import com.pukhovkirill.severstalnotes.entity.model.Image;

public interface ImageGateway {
    Image create(Image image) throws ImageAlreadyExistsException;
    Image update(Image image) throws ImageNotFoundException;
    void delete(Long imageId) throws ImageNotFoundException;

    Optional<Image> findByUrl(String url) throws ImageNotFoundException;

    List<Image> findAll();
}
