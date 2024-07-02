package com.farmacia.mediGood.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen a Cloudinary", e);
        }
    }


    public void deleteImage(String imageUrl) {
        try {
            Map deleteResult = cloudinary.uploader().destroy(imageUrl, ObjectUtils.emptyMap());

        } catch (IOException e) {
            throw new RuntimeException("Error al borrar la imagen de Cloudinary", e);
        }
    }
}