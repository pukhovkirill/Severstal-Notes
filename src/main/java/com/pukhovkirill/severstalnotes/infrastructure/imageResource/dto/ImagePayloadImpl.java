package com.pukhovkirill.severstalnotes.infrastructure.imageResource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;

@Getter
@Builder
@AllArgsConstructor
public class ImagePayloadImpl implements ImagePayload {

    private String url;

    private String name;

    private byte[] data;

    public ImagePayloadImpl(ImageResource resource) {
        this.url = resource.getUrl();
        this.name = resource.getName();
        this.data = resource.getData();
    }

}
