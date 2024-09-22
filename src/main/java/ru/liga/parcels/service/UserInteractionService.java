package ru.liga.parcels.service;

import ru.liga.parcels.util.UserAlgorithmChoice;

public interface UserInteractionService {
    int requestForNumberOfCars();

    String requestForFilePath();


    UserAlgorithmChoice requestForAlgorithmChoice();
}
