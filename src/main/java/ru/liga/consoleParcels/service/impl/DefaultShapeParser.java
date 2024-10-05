package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.service.ShapeParser;

@Service
@Log4j2
public class DefaultShapeParser implements ShapeParser {
    @Override
    public char[][] parseShape(String shape) {
        log.debug("Начинается парсинг формы: {}", shape);
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

        log.debug("Результат парсинга формы:\n{}", (Object[]) shapeArray);
        return shapeArray;
    }
}
