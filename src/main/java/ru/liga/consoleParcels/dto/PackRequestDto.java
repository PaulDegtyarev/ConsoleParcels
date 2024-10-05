package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

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
    private String trucks;
    private String inputData;
    private UserAlgorithmChoice algorithmChoice;
    private String filePathToWrite;
}
