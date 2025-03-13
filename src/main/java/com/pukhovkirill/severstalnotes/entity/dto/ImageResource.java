package com.pukhovkirill.severstalnotes.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ImageResource {

    private String url;

    private String name;

    private byte[] data;

}
