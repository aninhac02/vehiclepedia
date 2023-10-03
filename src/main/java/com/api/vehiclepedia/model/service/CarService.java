package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Car;
import com.api.vehiclepedia.model.entity.Vehicle;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CarService extends VehicleService{
    @Override
    public Car getVehicle(String url) {
        String stringFipeData = fipeExternalRequisitionService.getInfo(url);

        JSONParser parser = new JSONParser();
        JSONObject jsonFipeData;
        try {
            jsonFipeData  = (JSONObject) parser.parse(stringFipeData);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Car car = new Car();
        car.setBrand(jsonFipeData.getAsString("Marca"));
        car.setModel(jsonFipeData.getAsString("Modelo"));
        car.setYear(jsonFipeData.getAsString("AnoModelo"));
        car.setPrice(jsonFipeData.getAsString("Valor"));
        car.setFuel(jsonFipeData.getAsString("Combustivel"));
        car.setFipeCode(jsonFipeData.getAsString("CodigoFipe"));
        car.setReferenceMonth(jsonFipeData.getAsString("MesReferencia"));
        car.setFuelAcronym(jsonFipeData.getAsString("SiglaCombustivel"));

        return car;
    }
}
