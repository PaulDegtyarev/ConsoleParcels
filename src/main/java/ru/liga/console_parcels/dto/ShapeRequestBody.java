package ru.liga.console_parcels.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * DTO для передачи данных о форме посылки.
 */
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ShapeRequestBody {
    private final String shape;
}
