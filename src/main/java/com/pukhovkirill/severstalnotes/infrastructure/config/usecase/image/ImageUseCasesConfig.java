package com.pukhovkirill.severstalnotes.infrastructure.config.usecase.image;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.pukhovkirill.severstalnotes.entity.gateway.ImageGateway;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.usecase.image.UploadImageUseCase;
import com.pukhovkirill.severstalnotes.usecase.image.DeleteImageUseCase;
import com.pukhovkirill.severstalnotes.usecase.image.GetImageUseCase;

@Configuration
public class ImageUseCasesConfig {

    @Bean
    @Scope("prototype")
    public DeleteImageUseCase deleteImageUseCase(
            ImageGateway imageGateway,
            ImageStorageGateway imageStorageGateway
    ) {
        return new DeleteImageUseCase(imageGateway, imageStorageGateway);
    }

    @Bean
    @Scope("prototype")
    public GetImageUseCase getImageUseCase(ImageStorageGateway imageStorageGateway) {
        return new GetImageUseCase(imageStorageGateway);
    }

    @Bean
    @Scope("prototype")
    public UploadImageUseCase uploadImageUseCase(
            ImageGateway imageGateway,
            ImageStorageGateway imageStorageGateway
    ) {
        return new UploadImageUseCase(imageGateway, imageStorageGateway);
    }
}
