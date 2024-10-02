package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.mapper.ParcelMapper;

import java.util.List;

public interface PackageReader {
    List<ParcelMapper> readPackages(String filename);
}
