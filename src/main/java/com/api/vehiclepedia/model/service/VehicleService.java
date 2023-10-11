package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Vehicle;
import com.api.vehiclepedia.model.service.aws_cache_service.S3CacheService;
import feign.FeignException;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class VehicleService {

    @Autowired
    FipeExternalRequisitionService fipeExternalRequisitionService;

    @Autowired
    S3CacheService s3CacheService;

    public String getInfo(String url) throws Exception {
        String cachedFipeData;
        String stringFipeData;
        try {
            cachedFipeData = s3CacheService.getCachedData();

            if (cachedFipeData != null) {
                stringFipeData = cachedFipeData;

            } else {
                stringFipeData = fipeExternalRequisitionService.getInfo(url);
               // cachedFipeData = parseJson(stringFipeData);
               s3CacheService.putDataInCache(stringFipeData);
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

    public JSONObject getData(String url) {
        String stringFipeData;
        JSONObject jsonFipeData = new JSONObject();
        //jsonFipeData = s3CacheService.getCachedData();

        if (jsonFipeData.isEmpty()) {
           // stringFipeData = fipeExternalRequisitionService.getInfo(url);
           // jsonFipeData = parseJson(stringFipeData);
            //s3CacheService.putDataInCache(jsonFipeData);
        }
        return jsonFipeData;

    }
}
