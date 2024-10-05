package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс DTO для представления посылки, готовой к упаковке.
 *
 * <p>
 * Этот класс представляет посылку, включая ее высоту, ширину и форму.
 * Форма посылки представлена двумерным массивом символов.
 * Класс также предоставляет метод для расчета площади посылки.
 * </p>
 */
@Getter
@AllArgsConstructor
public class ParcelForPackagingDto {
    private final int height;
    private final int width;
    private char[][] shape;

    /**
     * Метод для получения площади посылки.
     *
     * @return Площадь посылки, рассчитанная как произведение ширины и высоты.
     */
    public int getArea() {
        return width * height;
    }
}