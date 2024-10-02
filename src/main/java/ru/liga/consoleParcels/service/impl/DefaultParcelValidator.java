package ru.liga.consoleParcels.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.exception.InvalidCharacterException;
import ru.liga.consoleParcels.exception.InvalidShapeException;
import ru.liga.consoleParcels.service.ParcelValidator;

@Service
public class DefaultParcelValidator implements ParcelValidator {
    @Override
    public void validateParcelShape(String shape) {
        if (shape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }
    }

    @Override
    public void validateParcelSymbol(char symbol) {
        if (symbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }
    }
}
