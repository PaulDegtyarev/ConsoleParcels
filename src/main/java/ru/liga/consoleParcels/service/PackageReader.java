package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackagingDto;

import java.util.List;

public interface PackageReader {

    List<ParcelForPackagingDto> readPackages(String filename);
}
