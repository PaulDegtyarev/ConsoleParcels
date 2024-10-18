package ru.liga.cargomanagement.service.impl.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.exception.InvalidInputFormatException;
import ru.liga.cargomanagement.service.BotCommand;
import ru.liga.cargomanagement.service.ParcelService;

@Service
@RequiredArgsConstructor
public class UpdateSymbolCommand implements BotCommand {
    private final ParcelService parcelService;
    private static final int INDEX_OF_FIRST_CHAR_OF_INPUT_DATA = 20;
    private static final int VALID_PARTS_LENGTH = 2;
    private static final int INDEX_OF_NAME = 0;
    private static final int INDEX_OF_SYMBOL = 1;
    private static final int SYMBOL_POSITION = 0;

    @Override
    public String execute(String messageText) {
        messageText = messageText.substring(INDEX_OF_FIRST_CHAR_OF_INPUT_DATA);

        String[] parts = messageText.split("; ");
        if (parts.length != VALID_PARTS_LENGTH) {
            throw new InvalidInputFormatException("Пожалуйста, укажите имя и новый символ. Формат: /updateSymbolByName имя; новый символ");
        }

        String parcelName = parts[INDEX_OF_NAME];
        char newSymbol = parts[INDEX_OF_SYMBOL].charAt(SYMBOL_POSITION);

        return parcelService.updateSymbolByParcelName(parcelName, newSymbol).toString();
    }
}
