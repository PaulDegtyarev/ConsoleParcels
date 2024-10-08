package ru.liga.console_parcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.ParcelRequestDto;
import ru.liga.console_parcels.exception.InvalidCharacterException;
import ru.liga.console_parcels.exception.InvalidShapeException;
import ru.liga.console_parcels.service.ParcelRequestValidator;

@Service
@Log4j2
public class DefaultParcelRequestValidator implements ParcelRequestValidator {

    @Override
    public void validateParcelShape(String shape) {
        log.info("Проверка валидности формы посылки: {}", shape);
        if (shape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }
    }

    @Override
    public void validateParcelSymbol(char symbol) {
        log.info("Проверка валидности символа посылки: {}", symbol);
        if (symbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }
    }

    @Override
    public boolean isThereSymbolThatIsNotSpecified(ParcelRequestDto parcelRequestDto) {
        for (char c : parcelRequestDto.getShape().toCharArray()) {
            if (c != parcelRequestDto.getSymbol() && c != ' ') {
                return true;
            }
        }
        return false;
    }
}
