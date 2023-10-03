package com.api.vehiclepedia.model.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "tabela.fipe.api", url = "${url.api.tabela.fipe}")
public interface FipeExternalRequisitionService {

    @GetMapping(value = "{url}")
    public String getInfo(@PathVariable String url);
}
