package ru.liga.cargomanagement.mapper.impl;

import org.springframework.stereotype.Component;
import ru.liga.cargomanagement.dto.PackRequestDto;
import ru.liga.cargomanagement.dto.enums.TruckPackageAlgorithm;
import ru.liga.cargomanagement.exception.WrongMessageFormatException;
import ru.liga.cargomanagement.mapper.DocumentMessageMapper;

import java.io.File;

@Component
public class DefaultDocumentMessageMapper implements DocumentMessageMapper {
    private final static int INDEX_OF_TRUCKS = 0;
    private final static int INDEX_OF_PACKAGE_ALGORITHM = 1;
    private final static int INDEX_OF_OUTPUT_FILEPATH = 2;
    private final static int CORRECT_COMMAND_LENGTH = 3;

    @Override
    public PackRequestDto toRequest(File file, String messageText) {
        String[] parts = messageText.split("; ");
        if (parts.length != CORRECT_COMMAND_LENGTH) {
            throw new WrongMessageFormatException("Неправильный формат сообщения. Пожалуйста, отправьте сообщение в формате 6x6; MAX_SPACE; output_filepath.json.");
        }

        String trucks = parts[INDEX_OF_TRUCKS];
        TruckPackageAlgorithm algorithmChoice = TruckPackageAlgorithm.valueOf(parts[INDEX_OF_PACKAGE_ALGORITHM]);
        String filePathToWrite = parts[INDEX_OF_OUTPUT_FILEPATH];
        String inputFilePath = file.getAbsolutePath();

        return new PackRequestDto(trucks, inputFilePath, algorithmChoice, filePathToWrite);
    }
}
