package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Truck;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TruckService extends VehicleService {

    @Override
    public Truck getVehicle(String url) throws Exception {
        String stringFipeData;
        try {
            stringFipeData = fipeExternalRequisitionService.getInfo(url);
        } catch (Exception e) {
            throw new Exception("não foi possível acessar os dados, por favor verifique as informações enviadas.");
        }

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
