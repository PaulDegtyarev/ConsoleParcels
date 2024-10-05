package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.exception.InvalidCharacterException;
import ru.liga.consoleParcels.exception.InvalidShapeException;

/**
 * Интерфейс для валидации посылок.
 */
public interface ParcelValidator {
    /**
     * Проверяет валидность формы посылки.
     *
     * @param shape Форма посылки в виде строки.
     * @throws InvalidShapeException Если форма посылки невалидна.
     */
    void validateParcelShape(String shape);

    /**
     * Проверяет валидность символа посылки.
     *
     * @param symbol Символ посылки.
     * @throws InvalidCharacterException Если символ посылки невалиден.
     */
    void validateParcelSymbol(char symbol);
}
