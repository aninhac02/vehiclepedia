package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Motorcycle;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MotorcycleService extends VehicleService {

    @Override
    public Motorcycle getVehicle(String url) throws Exception {
        JSONObject jsonFipeData;
        try {
            jsonFipeData = getData(url);
        } catch (Exception e) {
            throw new Exception("Erro - não foi possível acessar os dados, por favor verifique as informações enviadas.");
        }

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

