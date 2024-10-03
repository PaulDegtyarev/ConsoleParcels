package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackRequest {
    private String trucks;
    private String inputData;
    private UserAlgorithmChoice algorithmChoice;
    private String filePathToWrite;
}
