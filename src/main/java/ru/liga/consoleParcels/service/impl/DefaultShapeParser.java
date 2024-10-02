package ru.liga.consoleParcels.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.service.ShapeParser;

@Service
public class DefaultShapeParser implements ShapeParser {
    @Override
    public char[][] parseShape(String shape) {
        String[] lines = shape.split(" ");
        int height = lines.length;
        int maxWidth = 0;

        for (String line : lines) {
            maxWidth = Math.max(maxWidth, line.length());
        }

        char[][] shapeArray = new char[height][maxWidth];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                shapeArray[i][j] = lines[i].charAt(j);
            }
            for (int j = lines[i].length(); j < maxWidth; j++) {
                shapeArray[i][j] = ' ';
            }
        }

        return shapeArray;
    }
}
