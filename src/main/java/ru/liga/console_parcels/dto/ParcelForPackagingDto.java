package ru.liga.console_parcels.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class ParcelForPackagingDto {
    private final int height;
    private final int width;
    private final char[][] shape;

    /**
     * Метод для получения площади посылки.
     *
     * @return Площадь посылки, рассчитанная как произведение ширины и высоты.
     */
    public int getArea() {
        return width * height;
    }
}