package ru.liga.console_parcels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.liga.console_parcels.model.UserAlgorithmChoice;

/**
 * Класс DTO для запроса на упаковку посылок.
 *
 * <p>
 * Этот класс представляет данные, необходимые для запроса на упаковку посылок.
 * Он включает информацию о грузовиках, входных данных, выборе алгоритма и пути
 * к файлу для записи результата.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackRequestDto {
    @NotBlank private String trucks;
    @NotBlank private String inputData;
    @NotNull private UserAlgorithmChoice algorithmChoice;
    @NotBlank private String filePathToWrite;
}
