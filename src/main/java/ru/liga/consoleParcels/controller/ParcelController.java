package ru.liga.consoleParcels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.service.ParcelService;

@ShellComponent
public class ParcelController {
    private ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @ShellMethod
    public String findAllParcels() {
        return parcelService.findAllParcels();
    }

    @ShellMethod
    public ParcelResponseDto findParcelByName(String name) {
        return parcelService.findParcelByName(name);
    }

    @ShellMethod
    public ParcelResponseDto addParcel(String name, String shape, char symbol) {
        ParcelRequestDto parcelRequest = new ParcelRequestDto(name, shape, symbol);
        return parcelService.addParcel(parcelRequest);
    }

    @ShellMethod
    public ParcelResponseDto updateParcelByParcelName(String nameOfSavedParcel, String newShape, char newSymbol) {
        ParcelRequestDto parcelRequest = new ParcelRequestDto(nameOfSavedParcel, newShape, newSymbol);
        return parcelService.updateParcelByName(parcelRequest);
    }

    @ShellMethod
    public ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol) {
        return parcelService.updateSymbolByParcelName(nameOfSavedParcel, newSymbol);
    }

    @ShellMethod
    public ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String shape) {
        return parcelService.updateShapeByParcelName(nameOfSavedParcel, shape);
    }

    @ShellMethod
    public void deleteParcelByParcelName(String nameOfParcelForDelete) {
        parcelService.deleteParcelByParcelName(nameOfParcelForDelete);
    }
}
