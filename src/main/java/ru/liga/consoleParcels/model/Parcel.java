package ru.liga.consoleParcels.model;

import lombok.Getter;

/**
 * Представляет посылку с определенными характеристиками.
 * <p>
 * Посылка имеет высоту, ширину, идентификатор и форму,
 * которая представлена двумерным массивом символов.
 */
@Getter
public class Parcel {
    private final int height;
    private final int width;
    private final char symbol;
    private final String name;
    private char[][] shape;

    /**
     * Создает объект Parcel из строки ввода.
     * <p>
     * Строка ввода должна представлять форму посылки, где каждый
     * символ в строке представляет часть формы, а каждая новая
     * строка - новую строку формы.
     *
     * @param input Строка, представляющая форму посылки.
     */
    public Parcel(String input, char symbol, String name) {
        String[] lines = input.split("\n");

        this.height = lines.length;

        int maxWidth = 0;

        for (String line : lines) {
            maxWidth = Math.max(maxWidth, line.length());
        }

        this.width = maxWidth;
        this.symbol = symbol;
        this.name = name;

        this.shape = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                shape[i][j] = lines[i].charAt(j);
            }

            for (int j = lines[i].length(); j < width; j++) {
                shape[i][j] = ' ';
            }
        }
    }

    /**
     * Возвращает площадь посылки.
     *
     * @return Площадь посылки (ширина * высота).
     */
    public int getArea() {
        return width * height;
    }
}