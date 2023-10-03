package com.api.vehiclepedia.controller;

import com.api.vehiclepedia.model.entity.Truck;
import com.api.vehiclepedia.model.service.TruckService;
import com.api.vehiclepedia.utils.UrlsConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caminhoes/marcas")
public class TruckController {

    @Autowired
    TruckService truckService;
    @GetMapping()
    public String getBrands() {
        return truckService.getInfo(UrlsConsts.TRUCK_URL);
    }

    @GetMapping("/{brandCode}")
    public String getModels(@PathVariable String brandCode) {
        String url = UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL;
        return truckService.getInfo(url);
    }

    @GetMapping("/{brandCode}/{modelCode}")
    public String getYears(@PathVariable String brandCode, @PathVariable String modelCode) {
        String url = UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL;
        return truckService.getInfo(url);
    }

    @GetMapping("/{brandCode}/{modelCode}/{yearCode}")
    public Truck getCar(@PathVariable String brandCode, @PathVariable String modelCode, @PathVariable String yearCode) {
        String url = UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode;
        return truckService.getVehicle(url);
    }
}
