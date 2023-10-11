package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Vehicle;
import com.api.vehiclepedia.model.service.aws_cache_service.S3CacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public abstract class VehicleService {

    @Autowired
    FipeExternalRequisitionService fipeExternalRequisitionService;

    @Autowired
    S3CacheService s3CacheService;

    public String getInfo(String url) throws Exception {
        String cachedFipeData;
        String stringFipeData;
        String cacheKey = url;
        try {
            cachedFipeData = s3CacheService.getCachedData(cacheKey);

            if (cachedFipeData != null) {
                stringFipeData = cachedFipeData;

            } else {
                stringFipeData = fipeExternalRequisitionService.getInfo(url);
               // cachedFipeData = parseJson(stringFipeData);
               s3CacheService.putDataInCache(stringFipeData, cacheKey);
            }

            return stringFipeData;
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new Exception("Erro - não foi possível acessar os dados, por favor verifique as informações enviadas.");
        }

    }

    public JSONObject parseJson(String stringFipeData) {
        JSONParser parser = new JSONParser();
        JSONObject jsonFipeData;
        try {
            jsonFipeData  = (JSONObject) parser.parse(stringFipeData);
        } catch (ParseException e) {
            throw new RuntimeException("Erro - não foi possível converter os dados do JSON");
        }

        return jsonFipeData;
    }

    public abstract Vehicle getVehicle(String url) throws Exception;

    public JSONObject getData(String url) throws JsonProcessingException {
        String stringFipeData;
        String cacheKey = url;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            stringFipeData = s3CacheService.getCachedData(cacheKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (stringFipeData == null) {
           stringFipeData = fipeExternalRequisitionService.getInfo(url);
            try {
                s3CacheService.putDataInCache(stringFipeData, cacheKey);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return objectMapper.readValue(stringFipeData, JSONObject.class);

    }
}
