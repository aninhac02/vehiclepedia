package com.api.vehiclepedia.config;

import com.api.vehiclepedia.model.service.FipeExternalRequisitionService;
import com.api.vehiclepedia.model.service.FipeExternalRequisitionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public FipeExternalRequisitionServiceImpl fipeExternalRequisitionServiceImpl() {
        return new FipeExternalRequisitionServiceImpl();
    }
}
