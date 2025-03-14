package com.pukhovkirill.severstalnotes.infrastructure.imageResource.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pukhovkirill.severstalnotes.infrastructure.imageResource.dto.ImagePayloadImpl;
import com.pukhovkirill.severstalnotes.infrastructure.imageResource.service.ImageResourceStorageService;
import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;

@Controller
@RequiredArgsConstructor
public class ImageResourceController {

    private final ImageResourceStorageService imageResourceStorageService;

    @GetMapping("storage/{folder}/{filename}")
    public ResponseEntity<Resource> getImage(
            @PathVariable(value="folder") String folder,
            @PathVariable(value="filename") String filename
    ) {

        ImagePayload payload = ImagePayloadImpl.builder()
                        .url(folder+"/"+filename)
                        .name(filename)
                        .build();

        ImagePayload images = imageResourceStorageService.getImage(payload);

        final ByteArrayResource input = new ByteArrayResource(images.getData());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(input.contentLength())
                .body(input);
    }

}
