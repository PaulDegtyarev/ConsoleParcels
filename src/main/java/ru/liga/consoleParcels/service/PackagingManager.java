package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.PackRequest;

public interface PackagingManager {
    String packParcels(PackRequest packRequest);
}
