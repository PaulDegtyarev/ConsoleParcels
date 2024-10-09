package ru.liga.console_parcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.ParcelRequestDto;
import ru.liga.console_parcels.exception.InvalidCharacterException;
import ru.liga.console_parcels.exception.InvalidShapeException;
import ru.liga.console_parcels.service.ParcelRequestValidator;

/**
 * Реализация валидатора для посылок.
 */
@Service
@Log4j2
public class DefaultParcelRequestValidator implements ParcelRequestValidator {

    /**
     * Проверяет валидность формы посылки.
     *
     * @param shape Форма посылки в виде строки.
     * @throws InvalidShapeException Если форма посылки невалидна.
     */
    @Override
    public void validateParcelShape(String shape) {
        log.debug("Проверка валидности формы посылки: {}", shape);
        if (shape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }
        log.debug("Форма посылки валидна");
    }

    /**
     * Проверяет валидность символа посылки.
     *
     * @param symbol Символ посылки.
     * @throws InvalidCharacterException Если символ посылки невалиден.
     */
    @Override
    public void validateParcelSymbol(char symbol) {
        log.debug("Проверка валидности символа посылки: {}", symbol);
        if (symbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }
        log.debug("Символ посылки валиден");
    }

    /**
     * Метод для проверки наличия в форме посылки символов, отличных от указанного.
     *
     * @return true, если в форме посылки есть символы, отличные от указанного символа и пробела, иначе false.
     */
    @Override
    public boolean isThereSymbolThatIsNotSpecified(ParcelRequestDto parcelRequestDto) {
        for (char c : parcelRequestDto.getShape().toCharArray()) {
            log.info("Проверка символа {}", c);
            if (c != parcelRequestDto.getSymbol() && c != ' ') {
                return true;
            }
        }
        return false;
    }
}
