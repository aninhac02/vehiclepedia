package com.api.vehiclepedia.controller;

import com.api.vehiclepedia.model.entity.Truck;
import com.api.vehiclepedia.model.service.TruckService;
import com.api.vehiclepedia.utils.UrlsConsts;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TruckControllerTests {

    @Mock
    private TruckService truckService;

    @InjectMocks
    private TruckController truckController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetBrands() throws Exception {
        String expectedResponse = "[\"Volvo\", \"Scania\", \"Mercedes-Benz\"]";
        when(truckService.getInfo(UrlsConsts.TRUCK_URL)).thenReturn(expectedResponse);

        String actualResponse = truckController.getBrands();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldGetModels() throws Exception {
        String brandCode = "1";
        String expectedResponse = "[{\"codigo\":1,\"nome\":\"Integra GS 1.8\"}, {\"codigo\":2,\"nome\":\"Legend 3.2/3.5\"}]";
        when(truckService.getInfo(UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL)).thenReturn(expectedResponse);

        String actualResponse = truckController.getModels(brandCode);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldGetYears() throws Exception {
        String brandCode = "1";
        String modelCode = "1";
        String expectedResponse = "{\"codigo\":\"1992-1\",\"nome\":\"1992 Gasolina\"}, {\"codigo\":\"1991-1\",\"nome\":\"1991 Gasolina\"}]";

        when(truckService.getInfo(UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL)).thenReturn(expectedResponse);

        String actualResponse = truckController.getYears(brandCode, modelCode);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldGetTruck() throws Exception {
        String brandCode = "1";
        String modelCode = "1";
        String yearCode = "1992-1";
        Truck expectedTruck = new Truck();
        expectedTruck.setBrand(brandCode);
        expectedTruck.setModel(modelCode);
        expectedTruck.setYear(yearCode);
        when(truckService.getVehicle(UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode)).thenReturn(expectedTruck);

        Truck actualTruck = truckController.getTruck(brandCode, modelCode, yearCode);

        assertEquals(expectedTruck, actualTruck);
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenBrandIsInvalid() {
        when(truckService.getInfo(UrlsConsts.TRUCK_URL)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> truckController.getBrands());
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenBrandCodeIsInvalid() {
        String brandCode = "6";

        when(truckService.getInfo(UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> truckController.getModels(brandCode));
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenModelCodeIsInvalid() {

        String brandCode = "6";
        String modelCode = "43";
        when(truckService.getInfo(UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL)).thenThrow(new Exception());
        assertThrows(Exception.class, () -> truckController.getYears(brandCode, modelCode));
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenCarServiceThrowsException() {
        String brandCode = "6";
        String modelCode = "43";
        String yearCode = "1995-1";
        when(truckService.getVehicle(UrlsConsts.TRUCK_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> truckController.getTruck(brandCode, modelCode, yearCode));
    }
}
