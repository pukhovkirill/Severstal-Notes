package com.pukhovkirill.severstalnotes.infrastructure.imageResource.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.usecase.image.dto.ImagePayload;
import com.pukhovkirill.severstalnotes.usecase.image.UploadImageUseCase;
import com.pukhovkirill.severstalnotes.usecase.image.DeleteImageUseCase;
import com.pukhovkirill.severstalnotes.usecase.image.GetImageUseCase;
import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.infrastructure.imageResource.dto.ImagePayloadImpl;

@Service
@RequiredArgsConstructor
public class ImageResourceStorageServiceImpl implements ImageResourceStorageService {

    private final BeanFactory beanFactory;

    @Override
    public void uploadImage(ImagePayload image) {
        try{
            var uploadImageUseCase = (UploadImageUseCase) beanFactory.getBean(
                    "uploadImageUseCase",
                    UploadImageUseCase.class
            );

            uploadImageUseCase.execute(image);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImagePayload getImage(ImagePayload image) {
        ImagePayloadImpl imagePayload = null;
        try{
            var getImageUseCase = (GetImageUseCase) beanFactory.getBean(
                    "getImageUseCase",
                    GetImageUseCase.class
            );

            List<ImageResource> imageResources = getImageUseCase.execute(image);
            if(imageResources.size() == 1){
                imagePayload = new ImagePayloadImpl(imageResources.get(0));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return imagePayload;
    }

    @Override
    public void deleteImage(ImagePayload image) {
        try{
            var deleteImageUseCase = (DeleteImageUseCase) beanFactory.getBean(
                    "deleteImageUseCase",
                    DeleteImageUseCase.class
            );

            deleteImageUseCase.execute(image);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
