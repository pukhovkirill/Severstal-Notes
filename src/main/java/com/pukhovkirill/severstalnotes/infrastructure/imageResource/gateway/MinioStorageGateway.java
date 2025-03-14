package com.pukhovkirill.severstalnotes.infrastructure.imageResource.gateway;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;

import com.pukhovkirill.severstalnotes.entity.dto.ImageResource;
import com.pukhovkirill.severstalnotes.entity.gateway.ImageStorageGateway;
import com.pukhovkirill.severstalnotes.entity.exception.note.NoteNotFoundException;
import com.pukhovkirill.severstalnotes.entity.exception.imageResource.ImageResourceAlreadyExistsException;

public class MinioStorageGateway implements ImageStorageGateway {

    @Value("${minio.default-bucket-name}")
    private String BUCKET_NAME;

    private final MinioClient client;

    public MinioStorageGateway(MinioClient client) {
        this.client = client;
    }

    @Override
    public Optional<ImageResource> findByUrl(String url) {
        try{
            var result = Optional.ofNullable(client.statObject(StatObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(url).build()));

            if(result.isEmpty())
                throw new NoteNotFoundException(url);

            var item = result.get();

            var baos = new ByteArrayOutputStream();

            InputStream is = client.getObject(
                    GetObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(url)
                            .build());

            byte[] buff = new byte[1024];

            int count;
            while ((count = is.read(buff)) >= 0){
                baos.write(buff, 0, count);
            }

            ImageResource resource = new ImageResource(
                    url, Paths.get(url).getFileName().toString(), baos.toByteArray()
            );

            baos.close();
            is.close();

            return Optional.of(resource);
        }catch (MinioException e){
            System.err.println("Error occurred: " + e);
            System.err.println("HTTP trace: " + e.httpTrace());
            throw new RuntimeException(e);
        }catch(IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
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
