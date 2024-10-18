package ru.liga.cargomanagement.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilePermission {
    TXT(".txt"),
    JSON(".json");

    private final String extension;
}
