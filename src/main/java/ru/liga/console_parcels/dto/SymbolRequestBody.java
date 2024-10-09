package ru.liga.console_parcels.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DTO для передачи данных о символе посылки.
 */
@Getter
@RequiredArgsConstructor
public class SymbolRequestBody {
    private final char symbol;
}
