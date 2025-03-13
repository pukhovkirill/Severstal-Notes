package com.pukhovkirill.severstalnotes.infrastructure.config.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pukhovkirill.severstalnotes.entity.model.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Optional<Image> findByUrl(String url);
}
