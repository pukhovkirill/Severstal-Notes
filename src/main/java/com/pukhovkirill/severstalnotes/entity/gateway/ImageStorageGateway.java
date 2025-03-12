package com.pukhovkirill.severstalnotes.entity.gateway;

import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;

public interface ImageStorageGateway {
    Optional<ImageResource> findByUrl(String url);
    void save(ImageResource entity);
    boolean existsByUrl(String url);
    void delete(ImageResource entity);
}
