package ru.liga.cargomanagement.mapper;

import ru.liga.cargomanagement.dto.ParcelRequestDto;

public interface TextMessageMapper {
    ParcelRequestDto toRequest(String messageText);
}
