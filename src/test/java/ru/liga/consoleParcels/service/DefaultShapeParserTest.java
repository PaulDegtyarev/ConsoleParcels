package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.Test;
import ru.liga.consoleParcels.service.impl.DefaultShapeParser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultShapeParserTest {
    private DefaultShapeParser shapeParser = new DefaultShapeParser();

    @Test
    void parseShape_withValidInput_shouldReturnCorrectArray() {
        String shape = "*** * * ***";
        char[][] expectedArray = {
                {'*', '*', '*'},
                {'*', ' ', ' '},
                {'*', ' ', ' '},
                {'*', '*', '*'}
        };

        char[][] resultArray = shapeParser.parseShape(shape);

        assertThat(resultArray)
                .isDeepEqualTo(expectedArray);
    }

    @Test
    void parseShape_withEmptyInput_shouldReturnEmptyArray() {
        String shape = "";
        char[][] expectedArray = {{}};

        char[][] resultArray = shapeParser.parseShape(shape);

        assertThat(resultArray)
                .isEqualTo(expectedArray);
    }

    @Test
    void parseShape_withSingleLineInput_shouldReturnCorrectArray() {
        String shape = "*****";
        char[][] expectedArray = {
                {'*', '*', '*', '*', '*'}
        };

        char[][] resultArray = shapeParser.parseShape(shape);

        assertThat(resultArray)
                .isEqualTo(expectedArray);
    }
}
