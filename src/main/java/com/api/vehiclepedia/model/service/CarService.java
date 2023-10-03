package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Car;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CarService extends VehicleService{
    @Override
    public Car getVehicle(String url) throws Exception {
        String stringFipeData;
        try {
            stringFipeData = fipeExternalRequisitionService.getInfo(url);
        } catch (Exception e) {
            throw new Exception("não foi possível acessar os dados, por favor verifique as informações enviadas.");
        }


        JSONObject jsonFipeData = parseJson(stringFipeData);

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
