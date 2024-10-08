package ru.liga.consoleParcels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Класс для представления посылки.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parcels", schema = "parcels")
public class Parcel {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "shape")
    private String shape;

    @Column(name = "symbol")
    private char symbol;

    /**
     * Обновляет форму посылки и символ, используемый для представления посылки.
     *
     * @param newSymbol Новый символ для представления посылки.
     */
    public void updateShapeWithNewSymbol(String shape, char newSymbol) {
        this.shape = shape;
        this.symbol = newSymbol;
    }

    /**
     * Обновляет форму посылки.
     *
     * @param newShape Новая матрица символов для формы посылки.
     */
    public void updateShape(String newShape) {
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

    /**
     * Преобразует строку в матрицу символов.
     *
     * @param str Строка для преобразования.
     * @return Матрица символов.
     */
    public char[][] convertStringToCharArray(String str) {
        String[] rows = str.split("\n");
        char[][] charArray = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            charArray[i] = rows[i].toCharArray();
        }
        return charArray;
    }
}