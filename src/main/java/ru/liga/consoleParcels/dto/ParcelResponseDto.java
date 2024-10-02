package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParcelResponseDto {
    private final String name;
    private final char[][] shape;
    private final char symbol;

    @Override
    public String toString() {
        StringBuilder shapeString = new StringBuilder();
        for (char[] row : shape) {
            for (char c : row) {
                shapeString.append(c);
            }
            shapeString.append("\n");
        }

        if (!shapeString.isEmpty()) {
            shapeString.setLength(shapeString.length() - 1);
        }

        return String.format("name=%s,\nshape=%s,\nsymbol=%s", name, shapeString, symbol);
    }
}
