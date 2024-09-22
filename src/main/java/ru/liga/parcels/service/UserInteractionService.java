package ru.liga.parcels.service;

import ru.liga.parcels.util.UserAlgorithmChoice;

public interface UserInteractionService {
    int requestForNumberOfCars();

    String requestForInputFilePath();


    UserAlgorithmChoice requestForAlgorithmChoice();

    String requestForFilePathToWrite();
}
