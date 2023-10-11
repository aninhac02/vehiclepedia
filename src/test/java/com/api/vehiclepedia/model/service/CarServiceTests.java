package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Car;
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
public class CarServiceTests {

    @Mock
    private FipeExternalRequisitionService fipeExternalRequisitionService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private S3CacheService s3CacheService;

    @InjectMocks
    private CarService carService;

    private static final String URL = "http://localhost:8080/carros/marcas/6/43/1995-1";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetCar() throws Exception {
        String stringFipeData = "{\"Marca\": \"Toyota\", \"Modelo\": \"Corolla\", \"AnoModelo\": \"2023\", \"Valor\": \"100000.00\", \"Combustivel\": \"Flex\", \"CodigoFipe\": \"000000-01\", \"MesReferencia\": \"08/2023\", \"SiglaCombustivel\": \"F\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonFipeData = objectMapper.readValue(stringFipeData, JSONObject.class);
        Car expectedCar = new Car();
        expectedCar.setBrand("Toyota");
        expectedCar.setModel("Corolla");
        expectedCar.setYear("2023");
        expectedCar.setPrice("100000.00");
        expectedCar.setFuel("Flex");
        expectedCar.setFipeCode("000000-01");
        expectedCar.setReferenceMonth("08/2023");
        expectedCar.setFuelAcronym("F");

        when(fipeExternalRequisitionService.getInfo(URL)).thenReturn(stringFipeData);
        when(vehicleService.getData(URL)).thenReturn(jsonFipeData);

        Car actualCar = carService.getVehicle(URL);

        assertTrue(EqualsBuilder.reflectionEquals(expectedCar, actualCar));
    }

    @Test
    public void shouldThrowExceptionWhenFipeExternalRequisitionServiceThrowsException() throws Exception {
        when(fipeExternalRequisitionService.getInfo(URL)).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> carService.getVehicle(URL));
    }

    @Test
    public void shouldThrowExceptionWhenJsonParseFails() throws Exception {
        String stringFipeData = "invalid json data";

        when(fipeExternalRequisitionService.getInfo(URL)).thenReturn(stringFipeData);

        assertThrows(Exception.class, () -> carService.getVehicle(URL));
    }
}
