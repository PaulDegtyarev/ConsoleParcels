package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.model.UserAlgorithmChoice;

public interface PackagingSelectionService {
    PackagingService selectPackagingService(UserAlgorithmChoice algorithmChoice);
}
