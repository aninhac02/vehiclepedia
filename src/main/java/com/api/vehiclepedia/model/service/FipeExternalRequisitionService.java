package com.api.vehiclepedia.model.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient("url.api.tabela.fipe")
public interface FipeExternalRequisitionService {

    @GetMapping("/")
    public String getInfo(@RequestParam String url);
}
