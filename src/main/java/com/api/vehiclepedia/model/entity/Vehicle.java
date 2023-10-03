package com.api.vehiclepedia.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    private String brand = "";
    private String model = "";
    private String year = "";
    private String price = "";
    private String fuel = "";
    private String fipeCode = "";
    private String referenceMonth = "";
    private String fuelAcronym = "";


}
