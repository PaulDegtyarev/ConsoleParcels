package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.model.UserCommand;

/**
 * Сервис для получения запросов от пользователя.
 *
 * Этот интерфейс определяет методы для получения от
 * пользователя выбора действия (упаковка, распаковка,
 * выход) и необходимых параметров для выполнения этих
 * действий.
 */
public interface ReceivingUserRequestService {
    /**
     * Запрашивает у пользователя выбор действия.
     *
     * @return Выбранное пользователем действие.
     */
    UserCommand requestUserChoice();

    /**
     * Запрашивает у пользователя параметры для упаковки.
     *
     * @return Объект {@link PackagingParametersDto},
     *         содержащий параметры для упаковки.
     */
    PackagingParametersDto requestParametersForPacking();

    /**
     * Запрашивает у пользователя путь к файлу для распаковки
     * грузовика.
     *
     * @return Путь к файлу для распаковки грузовика.
     */
    String requestForFilePathToUnpackTruck();
}
