package com.api.vehiclepedia.controller;

import com.api.vehiclepedia.model.entity.Car;
import com.api.vehiclepedia.model.service.CarService;
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
public class CarControllerTests {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    public void shouldGetBrands() {
        String expectedResponse = "[{\"Acura\", \"Audi\", \"Volkswagen\"}]";
        when(carService.getInfo(UrlsConsts.CAR_URL)).thenReturn(expectedResponse);

        String actualResponse = carController.getBrands();

        assertEquals(expectedResponse, actualResponse);
    }

    @SneakyThrows
    @Test
    public void shouldGetModels() {
        String brandCode = "6";
        String expectedResponse = "[{\"codigo\":43,\"nome\":\"100 2.8 V6\"}, {\"codigo\":44,\"nome\":\"100 2.8 V6 Avant\"}]";
        when(carService.getInfo(UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL)).thenReturn(expectedResponse);

        String actualResponse = carController.getModels(brandCode);

        assertEquals(expectedResponse, actualResponse);
    }

    @SneakyThrows
    @Test
    public void shouldGetYears() {
        String brandCode = "6";
        String modelCode = "43";
        String expectedResponse = "[{\"codigo\":\"1995-1\",\"nome\":\"1995 Gasolina\"},{\"codigo\":\"1994-1\",\"nome\":\"1994 Gasolina\"},{\"codigo\":\"1993-1\",\"nome\":\"1993 Gasolina\"}]";
        when(carService.getInfo(UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL)).thenReturn(expectedResponse);

        String actualResponse = carController.getYears(brandCode, modelCode);

        assertEquals(expectedResponse, actualResponse);
    }

    @SneakyThrows
    @Test
    public void shouldGetCar() {
        String brandCode = "6";
        String modelCode = "43";
        String yearCode = "1995-1";
        Car expectedCar = new Car();
        expectedCar.setBrand(brandCode);
        expectedCar.setModel(modelCode);
        expectedCar.setYear(yearCode);
        when(carService.getVehicle(UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode)).thenReturn(expectedCar);

        Car actualCar = carController.getCar(brandCode, modelCode, yearCode);

        assertEquals(expectedCar, actualCar);
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenBrandIsInvalid() {
        when(carService.getInfo(UrlsConsts.CAR_URL)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> carController.getBrands());
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenBrandCodeIsInvalid() {
        String brandCode = "6";

        when(carService.getInfo(UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> carController.getModels(brandCode));
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenModelCodeIsInvalid() {

        String brandCode = "6";
        String modelCode = "43";
        when(carService.getInfo(UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL)).thenThrow(new Exception());
        assertThrows(Exception.class, () -> carController.getYears(brandCode, modelCode));
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenCarServiceThrowsException() {
        // Given
        String brandCode = "6";
        String modelCode = "43";
        String yearCode = "1995-1";
        when(carService.getVehicle(UrlsConsts.CAR_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode)).thenThrow(new Exception());

        // When
        assertThrows(Exception.class, () -> carController.getCar(brandCode, modelCode, yearCode));
    }
}

