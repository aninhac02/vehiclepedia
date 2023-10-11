package com.api.vehiclepedia.model.service.aws_cache_service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class S3CacheService {

    private final AmazonS3 amazonS3;
    private final String bucketName;
    private final String cacheKey;

    @Autowired
    public S3CacheService(AmazonS3 amazonS3, @Value("${aws.bucketName}") String bucketName, @Value("${aws.cache.key}") String cacheKey) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
        this.cacheKey = cacheKey;
    }

    public String getCachedData() throws IOException {
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

    public void putDataInCache(String jsonData) throws IOException {
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
