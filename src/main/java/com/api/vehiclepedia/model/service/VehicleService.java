package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Vehicle;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class VehicleService {

    @Autowired
    FipeExternalRequisitionService fipeExternalRequisitionService;

    public String getInfo(String url) {
        return fipeExternalRequisitionService.getInfo(url);
    }

    public JSONObject parseJson(String stringFipeData) {
        JSONParser parser = new JSONParser();
        JSONObject jsonFipeData;
        try {
            jsonFipeData  = (JSONObject) parser.parse(stringFipeData);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonFipeData;
    }

    public abstract Vehicle getVehicle(String url);
}
