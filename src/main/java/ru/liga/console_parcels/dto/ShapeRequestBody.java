package ru.liga.console_parcels.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DTO для передачи данных о форме посылки.
 */
@Getter
@RequiredArgsConstructor
public class ShapeRequestBody {
    private final String shape;
}
