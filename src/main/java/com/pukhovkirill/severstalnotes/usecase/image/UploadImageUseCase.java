package com.pukhovkirill.severstalnotes.usecase.image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.model.Image;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageGateway;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.entity.exception.image.ImageAlreadyExistsException;
import com.pukhovkirill.severstalnotes.entity.exception.imageResource.ImageResourceAlreadyExistsException;

@RequiredArgsConstructor
public class UploadImageUseCase implements UseCase<List<Image>, ImagePayload> {

    public final ImageGateway imageGateway;
    public final ImageStorageGateway imageStorageGateway;

    @Override
    public List<Image> execute(ImagePayload... args) {
        final List<Image> images;

        images = new ArrayList<>();
        if(args.length == 0)
            return images;

        Image image;
        ImageResource resource;

        int idx = 0;
        try{
            for(; idx < args.length; idx++){
                var img = args[idx];

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
        }catch(ImageAlreadyExistsException | ImageResourceAlreadyExistsException e){
            var subarray = Arrays.copyOfRange(args, idx+1, args.length);
            images.addAll(execute());
        }

        return images;
    }
}
