package com.api.vehiclepedia.controller;

import com.api.vehiclepedia.model.service.FipeExternalRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private FipeExternalRequisitionService fipeExternalRequsitionService;
    @GetMapping()
    public String get() {
        return fipeExternalRequsitionService.getInfo("/carros/marcas");
    }

}
