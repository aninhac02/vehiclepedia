package com.api.vehiclepedia.controller;

import com.api.vehiclepedia.model.entity.Motorcycle;
import com.api.vehiclepedia.model.service.MotorcycleService;
import com.api.vehiclepedia.utils.UrlsConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/motos/marcas")
public class MotorcycleController {

    @Autowired
    private MotorcycleService motorcycleService;
    @GetMapping()
    public String getBrands() throws Exception {
        return motorcycleService.getInfo(UrlsConsts.MOTORCYCLE_URL);
    }

    @GetMapping("/{brandCode}")
    public String getModels(@PathVariable String brandCode) throws Exception {
        String url = UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL;
        return motorcycleService.getInfo(url);
    }

    @GetMapping("/{brandCode}/{modelCode}")
    public String getYears(@PathVariable String brandCode, @PathVariable String modelCode) throws Exception {
        String url = UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL;
        return motorcycleService.getInfo(url);
    }

    @GetMapping("/{brandCode}/{modelCode}/{yearCode}")
    public Motorcycle getMotorcycle(@PathVariable String brandCode, @PathVariable String modelCode, @PathVariable String yearCode) throws Exception {
        String url = UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode;
        return motorcycleService.getVehicle(url);
    }
}
