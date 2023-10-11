package com.api.vehiclepedia.model.service.aws_cache_service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class S3CacheService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public S3CacheService(AmazonS3 amazonS3, @Value("${aws.bucketName}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public String getCachedData(String cacheKey) throws IOException {
        String cachedData;
            if (amazonS3.doesObjectExist(bucketName, cacheKey)) {
                S3Object object = amazonS3.getObject(bucketName, cacheKey);
                S3ObjectInputStream objectInputStream = object.getObjectContent();


                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(objectInputStream, StandardCharsets.UTF_8))) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);

                        }

                        cachedData = stringBuilder.toString();
                    }
                    return  cachedData;

            }

        return null;
    }

    public void putDataInCache(String jsonData, String cacheKey) throws IOException {
        writeInCacheFile(jsonData);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, cacheKey, new File("cached_data.txt"));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/json");
        metadata.addUserMetadata("test-api", "test-api");
        putObjectRequest.setMetadata(metadata);
        amazonS3.putObject(putObjectRequest);
    }

    public static MultipartFile convertJSONObjectToFile(JSONObject jsonData) throws IOException {
        try {
            String jsonString = jsonData.toString();

            File tempFile = File.createTempFile("temp", ".json");
            FileUtils.writeStringToFile(tempFile, jsonString, "UTF-8");
            MultipartFile multipartFile = (MultipartFile) tempFile.getAbsoluteFile();

            return multipartFile;
        } catch (IOException e) {
            return null;
        }

    }

    public static void writeInCacheFile(String jsonData) {


        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("cached_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonData);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
