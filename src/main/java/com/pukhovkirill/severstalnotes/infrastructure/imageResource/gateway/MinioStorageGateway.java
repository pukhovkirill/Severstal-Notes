package com.pukhovkirill.severstalnotes.infrastructure.imageResource.gateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.entity.exception.imageResource.ImageResourceAlreadyExistsException;

@Service
@Scope("prototype")
public class MinioStorageGateway implements ImageStorageGateway {

    @Value("${minio.default-bucket-name}")
    private String BUCKET_NAME;

    private final MinioClient client;

    public MinioStorageGateway(MinioClient client) {
        this.client = client;
    }

    @Override
    public Optional<ImageResource> findByUrl(String url) {
        return Optional.empty();
    }

    @Override
    public void save(ImageResource resource) {
        try{
            if(existsByUrl(resource.getUrl()))
                throw new ImageResourceAlreadyExistsException(resource.getUrl());

            byte[] buf = resource.getData();

            var putBuilder = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(resource.getUrl())
                    .stream(new ByteArrayInputStream(buf), buf.length, -1);

            client.putObject(putBuilder.build());
        }catch (MinioException e){
            System.err.println("Error occurred: " + e);
            System.err.println("HTTP trace: " + e.httpTrace());
            throw new RuntimeException(e);
        }catch(IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByUrl(String url) {
        try{
            var result = Optional.ofNullable(client.statObject(StatObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(url).build()));

            return result.isPresent();
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public void delete(ImageResource resource) {
        try{
            client.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(resource.getUrl())
                            .build());
        }catch (MinioException e){
            System.err.println("Error occurred: " + e);
            System.err.println("HTTP trace: " + e.httpTrace());
            throw new RuntimeException(e);
        }catch(IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
