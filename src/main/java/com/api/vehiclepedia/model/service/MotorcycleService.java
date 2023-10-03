package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Motorcycle;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MotorcycleService extends VehicleService {

    @Override
    public Motorcycle getVehicle(String url) throws Exception {
        String stringFipeData;
        try {
            stringFipeData = fipeExternalRequisitionService.getInfo(url);
        } catch (Exception e) {
            throw new Exception("não foi possível acessar os dados, por favor verifique as informações enviadas.");
        }

        JSONObject jsonFipeData = parseJson(stringFipeData);

        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setBrand(jsonFipeData.getAsString("Marca"));
        motorcycle.setModel(jsonFipeData.getAsString("Modelo"));
        motorcycle.setYear(jsonFipeData.getAsString("AnoModelo"));
        motorcycle.setPrice(jsonFipeData.getAsString("Valor"));
        motorcycle.setFuel(jsonFipeData.getAsString("Combustivel"));
        motorcycle.setFipeCode(jsonFipeData.getAsString("CodigoFipe"));
        motorcycle.setReferenceMonth(jsonFipeData.getAsString("MesReferencia"));
        motorcycle.setFuelAcronym(jsonFipeData.getAsString("SiglaCombustivel"));

        return motorcycle;
    }
}

