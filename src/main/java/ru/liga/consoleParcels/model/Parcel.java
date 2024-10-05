package ru.liga.consoleParcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс для представления посылки.
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Parcel {
    private final String name;
    private char[][] shape;
    private char symbol;

    /**
     * Обновляет форму посылки и символ, используемый для представления посылки.
     *
     * @param newShape  Новая матрица символов для формы посылки.
     * @param newSymbol Новый символ для представления посылки.
     */
    public void updateShapeWithNewSymbol(char[][] newShape, char newSymbol) {
        this.shape = newShape;
        this.symbol = newSymbol;
    }

    /**
     * Обновляет форму посылки.
     *
     * @param newShape Новая матрица символов для формы посылки.
     */
    public void updateShape(char[][] newShape) {
        this.shape = newShape;
    }

    /**
     * Возвращает строковое представление посылки.
     *
     * @return Строка, содержащая имя посылки.
     */
    @Override
    public String toString() {
        return "Parcel{" +
                "name='" + name + '\'' +
                '}';
    }
}