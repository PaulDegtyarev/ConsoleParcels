package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.PackRequestDto;

public interface PackagingManager {
    String packParcels(PackRequestDto packRequestDto);
}
