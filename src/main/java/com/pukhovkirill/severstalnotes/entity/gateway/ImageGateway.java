package com.pukhovkirill.severstalnotes.entity.gateway;

import java.util.List;
import java.util.Optional;

import com.pukhovkirill.severstalnotes.entity.model.Image;

public interface ImageGateway {
    void create(Image user);
    void update(Image user);
    void delete(Long userId);

    Optional<Image> findByEmail(String email);

    List<Image> findAll();
}
