package com.api.vehiclepedia.model.service;

import com.api.vehiclepedia.model.entity.Car;
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
