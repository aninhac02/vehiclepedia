package com.api.vehiclepedia.controller;

import com.api.vehiclepedia.model.entity.Motorcycle;
import com.api.vehiclepedia.model.service.MotorcycleService;
import com.api.vehiclepedia.utils.UrlsConsts;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MotorcycleControllerTests {

    @Mock
    private MotorcycleService motorcycleService;

    @InjectMocks
    private MotorcycleController motorcycleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    public void shouldGetBrands() {
        String expectedResponse = "[{\"HONDA\", \"HARLEY - DAVIDSON\", \"AVELLOZ\"}]";
        when(motorcycleService.getInfo(UrlsConsts.MOTORCYCLE_URL)).thenReturn(expectedResponse);

        String actualResponse = motorcycleController.getBrands();

        assertEquals(expectedResponse, actualResponse);
    }

    @SneakyThrows
    @Test
    public void shouldGetModels() {
        String brandCode = "77";
    String expectedResponse = "[{\"codigo\":5758, \"nome\":\"BLACKLINE FXS\"}, {\"codigo\":10609, \"nome\":\"BREAKOUT 117 FXBR\"}, {\"codigo\":6900, \"nome\":\"CVO BREAKOUT FXSBSE\"}, {\"codigo\":8157, \"nome\":\"CVO LIMITED 115th ANNIVERSA. FLHTKSE ANV\"}]";
        when(motorcycleService.getInfo(UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL)).thenReturn(expectedResponse);

        String actualResponse = motorcycleController.getModels(brandCode);

        assertEquals(expectedResponse, actualResponse);
    }

    @SneakyThrows
    @Test
    public void shouldGetYears() {
        String brandCode = "77";
        String modelCode = "5758";
        String expectedResponse = "[{\"codigo\":\"2013-1\",\"nome\":\"2013\"}, {\"codigo\":\"2012-1\",\"nome\":\"2012\"}]";
        when(motorcycleService.getInfo(UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL)).thenReturn(expectedResponse);

        String actualResponse = motorcycleController.getYears(brandCode, modelCode);

        assertEquals(expectedResponse, actualResponse);
    }

    @SneakyThrows
    @Test
    public void shouldGetCar() {
        String brandCode = "77";
        String modelCode = "5758";
        String yearCode = "2013-1";
        Motorcycle expectedMotorcycle = new Motorcycle();
        expectedMotorcycle.setBrand(brandCode);
        expectedMotorcycle.setModel(modelCode);
        expectedMotorcycle.setYear(yearCode);
        when(motorcycleService.getVehicle(UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode)).thenReturn(expectedMotorcycle);

        Motorcycle actualMotorcycle = motorcycleController.getMotorcycle(brandCode, modelCode, yearCode);

        assertEquals(expectedMotorcycle, actualMotorcycle);
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenBrandIsInvalid() {
        when(motorcycleService.getInfo(UrlsConsts.MOTORCYCLE_URL)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> motorcycleController.getBrands());
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenBrandCodeIsInvalid() {
        String brandCode = "6";

        when(motorcycleService.getInfo(UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> motorcycleController.getModels(brandCode));
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenModelCodeIsInvalid() {

        String brandCode = "6";
        String modelCode = "43";
        when(motorcycleService.getInfo(UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL)).thenThrow(new Exception());
        assertThrows(Exception.class, () -> motorcycleController.getYears(brandCode, modelCode));
    }

    @SneakyThrows
    @Test
    public void shouldThrowExceptionWhenCarServiceThrowsException() {
        // Given
        String brandCode = "6";
        String modelCode = "43";
        String yearCode = "1995-1";
        when(motorcycleService.getVehicle(UrlsConsts.MOTORCYCLE_URL + brandCode + UrlsConsts.MODELS_URL + modelCode + UrlsConsts.YEARS_URL + yearCode)).thenThrow(new Exception());

        // When
        assertThrows(Exception.class, () -> motorcycleController.getMotorcycle(brandCode, modelCode, yearCode));
    }
}
