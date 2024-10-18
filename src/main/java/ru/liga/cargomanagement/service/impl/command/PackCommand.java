package ru.liga.cargomanagement.service.impl.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.dto.PackRequestDto;
import ru.liga.cargomanagement.dto.enums.TruckPackageAlgorithm;
import ru.liga.cargomanagement.entity.Truck;
import ru.liga.cargomanagement.exception.InvalidInputFormatException;
import ru.liga.cargomanagement.formatter.ResultFormatter;
import ru.liga.cargomanagement.service.BotCommand;
import ru.liga.cargomanagement.service.PackagingManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackCommand implements BotCommand {
    private final PackagingManager packagingManager;
    private final ResultFormatter resultFormatter;
    private static final int INDEX_OF_FIRST_CHARACTER_OF_COMMAND = 6;
    private static final int CORRECT_COMMAND_LENGTH = 4;
    private static final int INDEX_OF_TRUCKS = 0;
    private static final int INDEX_OF_INPUT_DATA = 1;
    private static final int INDEX_OF_PACKAGE_ALGORITHM = 2;
    private static final int INDEX_OF_OUTPUT_FILEPATH = 3;

    @Override
    public String execute(String messageText) {
        String command = messageText.substring(INDEX_OF_FIRST_CHARACTER_OF_COMMAND);

        String[] parts = command.split("; ");
        if (parts.length != CORRECT_COMMAND_LENGTH) {
            throw new InvalidInputFormatException("Неправильный формат команды. Пожалуйста, отправьте команду в формате '/pack 6x6; входные данные; MAX_SPACE; output_filepath.json; данные'.");
        }

        String trucks = parts[INDEX_OF_TRUCKS];
        String inputData = parts[INDEX_OF_INPUT_DATA];
        TruckPackageAlgorithm algorithmChoice = TruckPackageAlgorithm.valueOf(parts[INDEX_OF_PACKAGE_ALGORITHM]);
        String filePathToWrite = parts[INDEX_OF_OUTPUT_FILEPATH];

        PackRequestDto packRequestDto = new PackRequestDto(trucks, inputData, algorithmChoice, filePathToWrite);

        List<Truck> packedTrucks = packagingManager.pack(packRequestDto);

        return resultFormatter.convertPackagingResultsToString(packedTrucks);
    }
}
