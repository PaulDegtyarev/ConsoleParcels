package ru.liga.cargomanagement.mapper;

import ru.liga.cargomanagement.dto.PackRequestDto;

import java.io.File;

public interface DocumentMessageMapper {
    PackRequestDto toRequest(File file, String messageText);
}
