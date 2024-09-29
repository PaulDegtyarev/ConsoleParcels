package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ParcelDto {
    private final String name;
    private char[][] shape;
    private char symbol;

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

        return "name=" + name + "shape=" + shapeString + ", symbol=" + symbol;
    }
}
