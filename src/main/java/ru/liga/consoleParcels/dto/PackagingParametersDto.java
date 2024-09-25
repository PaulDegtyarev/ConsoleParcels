package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class PackagingParametersDto {
    private int numberOfCars;
    private String inputFilePath;
    private UserAlgorithmChoice algorithmChoice;
    private String filePathToWrite;
}
