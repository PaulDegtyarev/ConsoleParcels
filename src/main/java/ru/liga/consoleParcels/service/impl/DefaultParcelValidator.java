package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.exception.InvalidCharacterException;
import ru.liga.consoleParcels.exception.InvalidShapeException;
import ru.liga.consoleParcels.service.ParcelValidator;

@Service
@Log4j2
public class DefaultParcelValidator implements ParcelValidator {
    @Override
    public void validateParcelShape(String shape) {
        log.debug("Проверка валидности формы посылки: {}", shape);
        if (shape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }
        log.debug("Форма посылки валидна");
    }

    @Override
    public void validateParcelSymbol(char symbol) {
        log.debug("Проверка валидности символа посылки: {}", symbol);
        if (symbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }
        log.debug("Символ посылки валиден");
    }
}
