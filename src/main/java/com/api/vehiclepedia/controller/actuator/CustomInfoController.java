package com.api.vehiclepedia.controller.actuator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/actuator/info")
public class CustomInfoController {

    @GetMapping
    public Map<String, String> customInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", "Vehiclepedia");
        info.put("description","API para obter iformações sobre veículos com base na Tabela Fipe");
        info.put("version", "1.0.0");
        info.put("author", "Ana Caroline Vilela");

        return info;
    }
}
