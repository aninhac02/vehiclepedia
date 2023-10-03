package com.api.vehiclepedia.controller;

import com.api.vehiclepedia.model.service.CarService;
import com.api.vehiclepedia.utils.UrlsConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carros/marcas")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping()
    public String getBrands() {
        return carService.getInfo(UrlsConsts.CAR_URL);
    }

    @GetMapping("/{brandCode}")
    public String getModels(@PathVariable String brandCode) {
        String url = UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL;
        return carService.getInfo(url);
    }

    @GetMapping("/{brandCode}/{modelCode}")
    public String getYears(@PathVariable String brandCode, @PathVariable String modelCode) {
        String url = UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL;
        return carService.getInfo(url);
    }

    @GetMapping("/{brandCode}/{modelCode}/{yearCode}")
    public String getCar(@PathVariable String brandCode, @PathVariable String modelCode, @PathVariable String yearCode) {
        String url = UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode;
        return carService.getInfo(url);
    }
}
