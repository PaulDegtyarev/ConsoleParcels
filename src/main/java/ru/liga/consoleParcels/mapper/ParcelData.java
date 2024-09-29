package ru.liga.consoleParcels.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParcelData {
    @JsonProperty("name")
    private String name;

    @JsonProperty("shape")
    private String shape;

    @JsonProperty("symbol")
    private String symbol;
}
