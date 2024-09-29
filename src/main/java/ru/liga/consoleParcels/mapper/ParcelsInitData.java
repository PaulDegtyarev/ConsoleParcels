package ru.liga.consoleParcels.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter
public class ParcelsInitData {
    @JsonProperty("parcels")
    private List<ParcelData> parcels;

    public ParcelsInitData(List<ParcelData> parcels) {
        this.parcels = parcels;
    }

    public ParcelsInitData(){}
}
