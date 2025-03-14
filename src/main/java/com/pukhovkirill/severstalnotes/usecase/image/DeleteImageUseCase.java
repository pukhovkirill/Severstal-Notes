package com.pukhovkirill.severstalnotes.usecase.image;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.entity.exception.imageResource.ImageResourceNotFoundException;

@RequiredArgsConstructor
public class DeleteImageUseCase implements UseCase<ImagePayload, ImagePayload> {

    public final ImageStorageGateway imageStorageGateway;

    @Override
    public ImagePayload execute(ImagePayload... args) {
        if(args == null || args.length == 0)
            return null;

        ImageResource resource;

        for(var img : args){
            resource = imageStorageGateway.findByUrl(img.getUrl())
                    .orElseThrow(() -> new ImageResourceNotFoundException(img.getUrl()));

            imageStorageGateway.delete(resource);
        }

        return args[args.length-1];
    }
}
