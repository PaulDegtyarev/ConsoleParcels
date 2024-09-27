package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.model.UserAlgorithmChoice;

/**
 * Сервис для выбора сервиса упаковки.
 * <p>
 * Этот интерфейс определяет метод для выбора сервиса
 * упаковки в зависимости от выбранного пользователем
 * алгоритма.
 */
public interface PackagingSelectionService {
    /**
     * Выбирает сервис упаковки в зависимости от выбранного
     * пользователем алгоритма.
     *
     * @param algorithmChoice Выбранный пользователем алгоритм
     *                        упаковки.
     * @return Сервис упаковки, соответствующий выбранному
     * алгоритму.
     */
    PackagingService selectPackagingService(UserAlgorithmChoice algorithmChoice);
}
