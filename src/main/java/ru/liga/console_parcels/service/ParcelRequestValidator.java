package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.ParcelRequestDto;
import ru.liga.console_parcels.exception.InvalidCharacterException;
import ru.liga.console_parcels.exception.InvalidShapeException;

/**
 * Интерфейс для валидации посылок.
 */
public interface ParcelRequestValidator {
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

    boolean isThereSymbolThatIsNotSpecified(ParcelRequestDto parcelRequestDto);
}
