package ru.liga.console_parcels.service;

import org.junit.jupiter.api.Test;
import ru.liga.console_parcels.service.impl.DefaultShapeParser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultShapeParserTest {
    private DefaultShapeParser shapeParser = new DefaultShapeParser();

    @Test
    void parse_withValidInput_shouldReturnCorrectArray() {
        String shape = "*** * * ***";
        char[][] expectedArray = {
                {'*', '*', '*'},
                {'*', ' ', ' '},
                {'*', ' ', ' '},
                {'*', '*', '*'}
        };

        char[][] resultArray = shapeParser.parse(shape);

        assertThat(resultArray)
                .isDeepEqualTo(expectedArray);
    }

    @Test
    void parse_withEmptyInput_shouldReturnEmptyArray() {
        String shape = "";
        char[][] expectedArray = {{}};

        char[][] resultArray = shapeParser.parse(shape);

        assertThat(resultArray)
                .isEqualTo(expectedArray);
    }

    @Test
    void parse_withSingleLineInput_shouldReturnCorrectArray() {
        String shape = "*****";
        char[][] expectedArray = {
                {'*', '*', '*', '*', '*'}
        };

        char[][] resultArray = shapeParser.parse(shape);

        assertThat(resultArray)
                .isEqualTo(expectedArray);
    }
}
