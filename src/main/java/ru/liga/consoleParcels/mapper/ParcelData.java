package ru.liga.consoleParcels.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ParcelData {
    @JsonProperty("name")
    private String name;

    @JsonProperty("shape")
    private String shape;

    @JsonProperty("symbol")
    private String symbol;

    public ParcelData(String name, String shape, String symbol) {
        this.name = name;
        this.shape = shape;
        this.symbol = symbol;
    }

    public ParcelData(){}
}
