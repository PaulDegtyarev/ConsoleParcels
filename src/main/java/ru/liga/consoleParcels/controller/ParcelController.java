package ru.liga.consoleParcels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.consoleParcels.dto.ParcelDto;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.service.ParcelService;

import java.util.List;

@ShellComponent
public class ParcelController {
    private ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @ShellMethod
    public List<ParcelDto> findAllParcels() {
        return parcelService.findAllParcels();
    }
}
