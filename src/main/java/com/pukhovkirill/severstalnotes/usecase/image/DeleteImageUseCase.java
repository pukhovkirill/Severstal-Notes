package com.pukhovkirill.severstalnotes.usecase.image;

import lombok.RequiredArgsConstructor;

import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.entity.model.Image;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageGateway;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.usecase.UseCase;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.entity.exception.image.ImageNotFoundException;
import com.pukhovkirill.severstalnotes.entity.exception.imageResource.ImageResourceNotFoundException;

@RequiredArgsConstructor
public class DeleteImageUseCase implements UseCase<ImagePayload, ImagePayload> {

    public final ImageGateway imageGateway;
    public final ImageStorageGateway imageStorageGateway;

    @Override
    public ImagePayload execute(ImagePayload... args) {
        if(args.length == 0)
            return null;

        Image image;
        ImageResource resource;

        for(var img : args){
            image = imageGateway.findByUrl(img.getUrl())
                    .orElseThrow(() -> new ImageNotFoundException(img.getUrl()));
            resource = imageStorageGateway.findByUrl(img.getUrl())
                    .orElseThrow(() -> new ImageResourceNotFoundException(img.getUrl()));

            imageGateway.delete(image.getId());
            imageStorageGateway.delete(resource);
        }

        return args[args.length-1];
    }
}
