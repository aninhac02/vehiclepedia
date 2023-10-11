package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Truck;
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
public class TruckServiceTests {

    @Mock
    private FipeExternalRequisitionService fipeExternalRequisitionService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private S3CacheService s3CacheService;

    @InjectMocks
    private TruckService truckService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static final String URL = "http://localhost:8080/motos/marcas/77/6408/2013-1";


    @Test
    public void shouldGetTruck() throws Exception {
        String stringFipeData = "{\"Marca\": \"Mercedes-Benz\", \"Modelo\": \"Accelo 1016\", \"AnoModelo\": \"2023\", \"Valor\": \"100000.00\", \"Combustivel\": \"Diesel\", \"CodigoFipe\": \"000000-01\", \"MesReferencia\": \"08/2023\", \"SiglaCombustivel\": \"D\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonFipeData = objectMapper.readValue(stringFipeData, JSONObject.class);
        Truck expectedTruck = new Truck();
        expectedTruck.setBrand("Mercedes-Benz");
        expectedTruck.setModel("Accelo 1016");
        expectedTruck.setYear("2023");
        expectedTruck.setPrice("100000.00");
        expectedTruck.setFuel("Diesel");
        expectedTruck.setFipeCode("000000-01");
        expectedTruck.setReferenceMonth("08/2023");
        expectedTruck.setFuelAcronym("D");

        when(fipeExternalRequisitionService.getInfo(URL)).thenReturn(stringFipeData);
        when(vehicleService.getData(URL)).thenReturn(jsonFipeData);

        Truck actualTruck = truckService.getVehicle(URL);

        assertTrue(EqualsBuilder.reflectionEquals(expectedTruck, actualTruck));
    }

    @Test
    public void shouldThrowExceptionWhenFipeExternalRequisitionServiceThrowsException() throws Exception {
        when(fipeExternalRequisitionService.getInfo(URL)).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> truckService.getVehicle(URL));
    }

    @Test
    public void shouldThrowExceptionWhenJsonParseFails() throws Exception {
        String stringFipeData = "invalid json data";

        when(fipeExternalRequisitionService.getInfo(URL)).thenReturn(stringFipeData);

        assertThrows(Exception.class, () -> truckService.getVehicle(URL));
    }
}

