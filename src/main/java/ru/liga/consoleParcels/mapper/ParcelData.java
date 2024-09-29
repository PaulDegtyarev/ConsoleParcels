package ru.liga.consoleParcels.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParcelData {
    @JsonProperty("name")
    private String name;

    @JsonProperty("shape")
    private String shape;

    @JsonProperty("symbol")
    private String symbol;

    public char[][] createShape() {
        String[] lines = shape.split("\n");
        int height = lines.length;
        int maxWidth = 0;

        // Определяем максимальную ширину строки
        for (String line : lines) {
            maxWidth = Math.max(maxWidth, line.length());
        }

        char[][] shapeMatrix = new char[height][maxWidth];

        for (int i = 0; i < height; i++) {
            String line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                shapeMatrix[i][j] = line.charAt(j);
            }
            // Заполняем оставшиеся ячейки пробелами
            for (int j = line.length(); j < maxWidth; j++) {
                shapeMatrix[i][j] = ' ';
            }
        }

        return shapeMatrix;
    }
}
