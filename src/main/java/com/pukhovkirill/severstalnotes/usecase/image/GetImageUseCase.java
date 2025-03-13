package com.pukhovkirill.severstalnotes.usecase.image;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.entity.exception.imageResource.ImageResourceNotFoundException;

@RequiredArgsConstructor
public class GetImageUseCase implements UseCase<List<ImageResource>, ImagePayload> {

    public final ImageStorageGateway imageStorageGateway;

    @Override
    public List<ImageResource> execute(ImagePayload... args) {
        if(args.length == 0)
            return null;

        final List<ImageResource> images;

        images = new ArrayList<>();
        ImageResource resource;
        for(var img : args){
            resource = imageStorageGateway.findByUrl(img.getUrl())
                    .orElseThrow(() -> new ImageResourceNotFoundException(img.getUrl()));

            images.add(resource);
        }

        return images;
    }
}
