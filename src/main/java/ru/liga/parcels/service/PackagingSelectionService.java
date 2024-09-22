package ru.liga.parcels.service;

import ru.liga.parcels.util.UserAlgorithmChoice;

public interface PackagingSelectionService {
    PackagingService selectPackagingService(UserAlgorithmChoice algorithmChoice);
}
