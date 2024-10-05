package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;

public interface ParcelService {

    String findAllParcels();

    ParcelResponseDto findParcelByName(String name);

    ParcelResponseDto addParcel(ParcelRequestDto parcelRequestDto);

    ParcelResponseDto updateParcelByName(ParcelRequestDto parcelRequestDto);

    ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol);

    ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String newShape);

    void deleteParcelByParcelName(String nameOfParcelForDelete);
}
