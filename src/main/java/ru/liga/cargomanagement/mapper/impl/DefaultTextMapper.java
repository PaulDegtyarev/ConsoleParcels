package ru.liga.cargomanagement.mapper.impl;

import org.springframework.stereotype.Component;
import ru.liga.cargomanagement.dto.ParcelRequestDto;
import ru.liga.cargomanagement.mapper.TextMessageMapper;

@Component
public class DefaultTextMapper implements TextMessageMapper {
    private static final int VALID_PARTS_LENGTH = 3;
    private static final int INDEX_OF_NAME = 0;
    private static final int INDEX_OF_SHAPE = 1;
    private static final int INDEX_OF_SYMBOL = 2;
    private static final int SYMBOL_POSITION = 0;

    @Override
    public ParcelRequestDto toRequest(String messageText) {
        String[] parts = messageText.split("; ");

        if (parts.length < VALID_PARTS_LENGTH) {
            throw new IllegalArgumentException("Неправильный формат данных для посылки");
        }

        String name = parts[INDEX_OF_NAME].trim();
        String shape = parts[INDEX_OF_SHAPE].trim();
        char symbol = parts[INDEX_OF_SYMBOL].charAt(SYMBOL_POSITION);

        return new ParcelRequestDto(name, shape, symbol);
    }
}
