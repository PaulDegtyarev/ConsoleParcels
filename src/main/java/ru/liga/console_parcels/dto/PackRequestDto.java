package ru.liga.console_parcels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс DTO для запроса на упаковку посылок.
 *
 * <p>
 * Этот класс представляет данные, необходимые для запроса на упаковку посылок.
 * Он включает информацию о грузовиках, входных данных, выборе алгоритма упаковки и пути
 * к файлу для записи результата.
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class PackRequestDto {
    @NotBlank
    private final String trucks;
    @NotBlank
    private final String inputData;
    @NotNull
    private final TruckPackageAlgorithm algorithmChoice;
    @NotBlank
    private final String filePathToWrite;
}
