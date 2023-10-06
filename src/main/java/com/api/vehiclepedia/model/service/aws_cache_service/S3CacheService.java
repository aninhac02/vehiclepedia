package com.api.vehiclepedia.model.service.aws_cache_service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class S3CacheService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public S3CacheService(AmazonS3 amazonS3, @Value("${aws.bucketName}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public JSONObject getCachedData(String cacheKey) throws IOException {
        if (amazonS3.doesObjectExist(bucketName, cacheKey)) {

            S3Object object = amazonS3.getObject(bucketName, cacheKey);
            S3ObjectInputStream objectInputStream = object.getObjectContent();

            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject cachedData = objectMapper.readValue(objectInputStream, JSONObject.class);

            return cachedData;
        }

        return null;
    }

    public void putDataInCache(JSONObject jsonData, String cacheKey) throws IOException {
        File jsonDataFile = convertJSONObjectToFile(jsonData);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, cacheKey, jsonDataFile);
        amazonS3.putObject(putObjectRequest);
    }

    public static File convertJSONObjectToFile(JSONObject jsonData) throws IOException {
        try {
            String filePath = "/cache/cached_data.json";
            File file = new File(filePath);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonData.toString());

            fileWriter.close();

            return file;
        } catch (IOException e) {
            return null;
        }



    }

}
