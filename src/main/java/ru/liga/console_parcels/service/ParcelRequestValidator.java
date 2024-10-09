package ru.liga.console_parcels.service;

import ru.liga.console_parcels.dto.ParcelRequestDto;
import ru.liga.console_parcels.exception.InvalidCharacterException;
import ru.liga.console_parcels.exception.InvalidShapeException;

/**
 * Интерфейс для валидации входящих данных о посылках.
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

    /**
     * Проверяет, есть ли символ, который не указан в списке допустимых символов.
     *
     * @param parcelRequestDto Запрос на добавление или обновление посылки.
     * @return {@code true}, если в запросе есть символ, который не был указан, иначе {@code false}.
     */
    boolean isThereSymbolThatIsNotSpecified(ParcelRequestDto parcelRequestDto);
}
