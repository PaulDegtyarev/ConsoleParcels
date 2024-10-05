package ru.liga.consoleParcels.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс для хранения данных о посылках.
 */
@Getter
@NoArgsConstructor
public class ParcelsInitData {
    @JsonProperty("parcels")
    private List<ParcelData> parcels;
}
