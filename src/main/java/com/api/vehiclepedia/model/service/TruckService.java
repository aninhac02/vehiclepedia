package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Motorcycle;
import com.api.vehiclepedia.model.entity.Truck;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class TruckService extends VehicleService {

    @Override
    public Truck getVehicle(String url) {
        String stringFipeData = fipeExternalRequisitionService.getInfo(url);

        JSONObject jsonFipeData = parseJson(stringFipeData);

        Truck truck = new Truck();
        truck.setBrand(jsonFipeData.getAsString("Marca"));
        truck.setModel(jsonFipeData.getAsString("Modelo"));
        truck.setYear(jsonFipeData.getAsString("AnoModelo"));
        truck.setPrice(jsonFipeData.getAsString("Valor"));
        truck.setFuel(jsonFipeData.getAsString("Combustivel"));
        truck.setFipeCode(jsonFipeData.getAsString("CodigoFipe"));
        truck.setReferenceMonth(jsonFipeData.getAsString("MesReferencia"));
        truck.setFuelAcronym(jsonFipeData.getAsString("SiglaCombustivel"));

        return truck;
    }
}
