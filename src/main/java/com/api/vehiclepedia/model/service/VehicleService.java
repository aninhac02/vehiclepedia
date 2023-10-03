package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class VehicleService {

    @Autowired
    FipeExternalRequisitionService fipeExternalRequisitionService;

    public String getInfo(String url) {
        return fipeExternalRequisitionService.getInfo(url);
    }

    public abstract Vehicle getVehicle(String url);
}
