package ru.liga.cargomanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * DTO для передачи данных о символе посылки.
 */
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SymbolRequestBody {
    private final char symbol;
}
