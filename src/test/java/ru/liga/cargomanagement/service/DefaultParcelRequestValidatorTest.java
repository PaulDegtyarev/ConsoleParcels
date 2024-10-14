package ru.liga.cargomanagement.service;

import org.junit.jupiter.api.Test;
import ru.liga.cargomanagement.exception.InvalidCharacterException;
import ru.liga.cargomanagement.exception.InvalidShapeException;
import ru.liga.cargomanagement.service.impl.DefaultParcelRequestValidator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class DefaultParcelRequestValidatorTest {
    private DefaultParcelRequestValidator parcelValidator = new DefaultParcelRequestValidator();

    @Test
    void validateParcelShape_withValidInput_shouldReturn() {
        String validShape = "***\n* *\n***";

        assertThatCode(() -> parcelValidator.validateParcelShape(validShape))
                .doesNotThrowAnyException();
    }

    @Test
    void validateParcelShape_withBlankInput_shouldThrowInvalidShapeException() {
        String blankShape = "   ";

        assertThatThrownBy(() -> parcelValidator.validateParcelShape(blankShape))
                .isInstanceOf(InvalidShapeException.class)
                .hasMessage("Новая форма не может быть пробелами");
    }

    @Test
    void validateParcelSymbol_withValidInput_shouldReturn() {
        char validSymbol = '*';

        assertThatCode(() -> parcelValidator.validateParcelSymbol(validSymbol))
                .doesNotThrowAnyException();
    }

    @Test
    void validateParcelSymbol_withSpaceInput_shouldThrowInvalidCharacterException() {
        char invalidSymbol = ' ';

        assertThatThrownBy(() -> parcelValidator.validateParcelSymbol(invalidSymbol))
                .isInstanceOf(InvalidCharacterException.class)
                .hasMessage("Нельзя сделать символ пробелом");
    }
}
