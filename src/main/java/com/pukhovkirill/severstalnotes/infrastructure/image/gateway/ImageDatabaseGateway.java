package com.pukhovkirill.severstalnotes.infrastructure.image.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.entity.gateway.ImageGateway;
import com.pukhovkirill.severstalnotes.entity.model.Image;
import com.pukhovkirill.severstalnotes.infrastructure.config.database.repository.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageDatabaseGateway implements ImageGateway {

    private final ImageRepository imageRepository;

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image update(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(Long imageId) {
        Optional<Image> image;
        if((image = imageRepository.findById(imageId)).isPresent()){
            imageRepository.delete(image.get());
        }
    }

    @Override
    public Optional<Image> findByUrl(String url) {
        return imageRepository.findByUrl(url);
    }

    @Override
    public List<Image> findAll() {
        final List<Image> images = new ArrayList<>();
        imageRepository.findAll().forEach(images::add);
        return images;
    }
}
