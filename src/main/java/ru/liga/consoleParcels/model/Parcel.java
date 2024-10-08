package ru.liga.consoleParcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Класс для представления посылки.
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Parcel {
    private final String name;

    @Setter
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