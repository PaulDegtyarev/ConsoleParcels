package ru.liga.cargomanagement.service.impl.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.exception.WrongMessageFormatException;
import ru.liga.cargomanagement.service.BotCommand;
import ru.liga.cargomanagement.service.ParcelService;

@Service
@RequiredArgsConstructor
public class DeleteCommand implements BotCommand {
    private final ParcelService parcelService;
    private static final int INDEX_OF_FIRST_CHAR_OF_INPUT_DATA = 14;
    private static final int VALID_PARTS_LENGTH = 1;
    private static final int INDEX_OF_NAME = 0;

    @Override
    public String execute(String messageText) {
        messageText = messageText.substring(INDEX_OF_FIRST_CHAR_OF_INPUT_DATA);

        String[] parts = messageText.split("; ");
        if (parts.length < VALID_PARTS_LENGTH) {
            throw new WrongMessageFormatException("Пожалуйста, укажите имя посылки для удаления. Формат: /deleteByName имя");

        }

        String parcelName = parts[INDEX_OF_NAME];
        parcelService.deleteParcelByParcelName(parcelName);

        return "Посылка успешно удалена";
    }
}
