package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelForPackaging;
import ru.liga.consoleParcels.mapper.ParcelMapper;

import java.util.List;

public interface PackageReader {

    List<ParcelForPackaging> readPackages(String filename);
}
