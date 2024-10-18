package ru.liga.cargomanagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.cargomanagement.dto.enums.ParcelCommandType;
import ru.liga.cargomanagement.service.BotCommand;
import ru.liga.cargomanagement.service.TextHandler;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultTextHandler implements TextHandler {
    private final Map<ParcelCommandType, BotCommand> parcelCommandMap;
    private final static int INDEX_OF_FIRST_WORD = 0;

    @Override
    public String handle(Message message) {
        String messageText = message.getText();
        String firstWord = messageText.split(" ")[INDEX_OF_FIRST_WORD];
        log.info(firstWord);

        ParcelCommandType parcelCommandType = ParcelCommandType.fromString(firstWord);
        log.info(parcelCommandType.toString());

        return parcelCommandMap.get(parcelCommandType)
                .execute(messageText);
    }
}