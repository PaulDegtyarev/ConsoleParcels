package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.model.UserAlgorithmChoice;

public interface UserInteractionService {
    int requestForNumberOfCars();

    String requestForInputFilePath();

    UserAlgorithmChoice requestForAlgorithmChoice();

    String requestForFilePathToWrite();
}
