package ru.liga.cargomanagement.service.impl.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.dto.ParcelRequestDto;
import ru.liga.cargomanagement.mapper.TextMessageMapper;
import ru.liga.cargomanagement.service.BotCommand;
import ru.liga.cargomanagement.service.ParcelService;

@Service
@RequiredArgsConstructor
public class UpdateCommand implements BotCommand {
    private final ParcelService parcelService;
    private final TextMessageMapper textMessageMapper;
    private static final int INDEX_OF_FIRST_CHAR_OF_INPUT_DATA = 14;

    @Override
    public String execute(String messageText) {
        messageText = messageText.substring(INDEX_OF_FIRST_CHAR_OF_INPUT_DATA);

        ParcelRequestDto parcelRequestDto = textMessageMapper.toRequest(messageText);

        return parcelService.updateByName(parcelRequestDto).toString();
    }
}
