package com.pukhovkirill.severstalnotes.usecase.image;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.model.Image;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageGateway;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;

@RequiredArgsConstructor
public class UploadImageUseCase implements UseCase<List<Image>, ImagePayload> {

    public final ImageGateway imageGateway;
    public final ImageStorageGateway imageStorageGateway;

    @Override
    public List<Image> execute(ImagePayload... args) {
        if(args.length == 0)
            return null;

        final List<Image> images;

        Image image;
        ImageResource resource;
        images = new ArrayList<>();
        for(var img : args){
            image = new Image();
            image.setUrl(img.getUrl());
            image.setPlace(image.getPlace());

            image = imageGateway.create(image);
            images.add(image);

            resource = ImageResource.builder()
                    .url(img.getUrl())
                    .name(img.getName())
                    .data(img.getData())
                    .build();

            imageStorageGateway.save(resource);
        }

        return images;
    }
}
