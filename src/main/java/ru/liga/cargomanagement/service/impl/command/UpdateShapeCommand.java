package ru.liga.cargomanagement.service.impl.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.exception.WrongMessageFormatException;
import ru.liga.cargomanagement.service.BotCommand;
import ru.liga.cargomanagement.service.ParcelService;

@Service
@RequiredArgsConstructor
public class UpdateShapeCommand implements BotCommand {
    private final ParcelService parcelService;
    private static final int INDEX_OF_FIRST_CHAR_OF_INPUT_DATA = 19;
    private static final int VALID_PARTS_LENGTH = 2;
    private static final int INDEX_OF_NAME = 0;
    private static final int INDEX_OF_SHAPE = 1;

    @Override
    public String execute(String messageText) {
        messageText = messageText.substring(INDEX_OF_FIRST_CHAR_OF_INPUT_DATA);

        String[] parts = messageText.split("; ");
        if (parts.length < VALID_PARTS_LENGTH) {
            throw new WrongMessageFormatException("Пожалуйста, укажите имя и новую форму. Формат: /updateShapeByName имя новая форма");

        }

        String parcelName = parts[INDEX_OF_NAME];
        String newShape = parts[INDEX_OF_SHAPE];
        return parcelService.updateShapeByParcelName(parcelName, newShape).toString();
    }
}
