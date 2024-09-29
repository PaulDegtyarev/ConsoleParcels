package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelDto;
import ru.liga.consoleParcels.model.Parcel;

import java.util.List;

public interface ParcelService {

    String findAllParcels();


    ParcelDto findParcelByName(String name);

    ParcelDto addParcel(String name, String shape, char symbol);
}
