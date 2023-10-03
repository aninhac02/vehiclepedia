package com.api.vehiclepedia.model.service;

import org.springframework.stereotype.Service;

@Service
public class CarService extends VehicleService{


    @Override
    public String getVehicle(String url) {
        return fipeExternalRequisitionService.getInfo(url);
    }
}
