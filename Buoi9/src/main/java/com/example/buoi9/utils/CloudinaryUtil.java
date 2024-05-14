package com.example.buoi9.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CloudinaryUtil {
    private final Cloudinary cloudinary;

    public File convertMultipartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream outputStream = new FileOutputStream(convertFile);
        outputStream.write(file.getBytes());
        outputStream.close();
        return convertFile;
    }

    public String getUrlFromFile(MultipartFile multipartFile) throws Exception {
        try {
            Map<?, ?> mapFile = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            //Todo : ObjectUtils.emptyMap : returns an empty 'java.util.Map' object
            //Todo : mapFile.get("secure_url") : is a key used in the metadata returned after uploading an image
            return mapFile.get("secure_url").toString();
        } catch (Exception exception) {
            throw new Exception("The process get url from file failed!");
        }
    }

    public String removeFileToUrl(String url) throws Exception {
        try {
            String publicId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "Remove file to url is successfully";
        } catch (Exception exception) {
            throw new Exception("Upload image failed");
        }
    }
}
