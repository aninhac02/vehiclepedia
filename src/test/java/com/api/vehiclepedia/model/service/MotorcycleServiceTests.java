package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Motorcycle;
import com.api.vehiclepedia.model.service.aws_cache_service.S3CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MotorcycleServiceTests {

    @Mock
    private FipeExternalRequisitionService fipeExternalRequisitionService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private S3CacheService s3CacheService;

    @InjectMocks
    private MotorcycleService motorcycleService;

    private static final String URL = "http://localhost:8080/motos/marcas/77/6408/2013-1";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetMotorcycle() throws Exception {
        String stringFipeData = "{\"Marca\": \"Honda\", \"Modelo\": \"CG 125\", \"AnoModelo\": \"2023\", \"Valor\": \"10000.00\", \"Combustivel\": \"Flex\", \"CodigoFipe\": \"000000-01\", \"MesReferencia\": \"08/2023\", \"SiglaCombustivel\": \"F\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonFipeData = objectMapper.readValue(stringFipeData, JSONObject.class);
        Motorcycle expectedMotorcycle = new Motorcycle();
        expectedMotorcycle.setBrand("Honda");
        expectedMotorcycle.setModel("CG 125");
        expectedMotorcycle.setYear("2023");
        expectedMotorcycle.setPrice("10000.00");
        expectedMotorcycle.setFuel("Flex");
        expectedMotorcycle.setFipeCode("000000-01");
        expectedMotorcycle.setReferenceMonth("08/2023");
        expectedMotorcycle.setFuelAcronym("F");

        when(fipeExternalRequisitionService.getInfo(URL)).thenReturn(stringFipeData);
        when(vehicleService.getData(URL)).thenReturn(jsonFipeData);

        Motorcycle actualMotorcycle= motorcycleService.getVehicle(URL);

        assertTrue(EqualsBuilder.reflectionEquals(expectedMotorcycle, actualMotorcycle));
    }

    @Test
    public void shouldThrowExceptionWhenFipeExternalRequisitionServiceThrowsException() throws Exception {
        when(fipeExternalRequisitionService.getInfo(URL)).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> motorcycleService.getVehicle(URL));
    }

    @Test
    public void shouldThrowExceptionWhenJsonParseFails() throws Exception {
        String stringFipeData = "invalid json data";

        when(fipeExternalRequisitionService.getInfo(URL)).thenReturn(stringFipeData);

        assertThrows(Exception.class, () -> motorcycleService.getVehicle(URL));
    }
}

