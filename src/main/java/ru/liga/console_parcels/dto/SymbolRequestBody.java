package ru.liga.console_parcels.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SymbolRequestBody {
    private final char symbol;
}
