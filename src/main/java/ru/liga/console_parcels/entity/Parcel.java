package ru.liga.console_parcels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс сущности посылки.
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

    @Setter
    @Column(name = "shape")
    private String shape;

    @Column(name = "symbol")
    private char symbol;

    /**
     * Обновляет форму посылки и символ, из которого состоит форма посылки.
     *
     * @param newShape  Новая форма посылки.
     * @param newSymbol Новый символ для представления посылки.
     */
    public void updateShapeWithNewSymbol(String newShape, char newSymbol) {
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

    /**
     * Преобразует строку в матрицу символов.
     *
     * @param inputString Строка для преобразования.
     * @return Матрица символов.
     */
    public char[][] convertStringToCharArray(String inputString) {
        String[] rows = inputString.split("\n");
        char[][] charArray = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            charArray[i] = rows[i].toCharArray();
        }
        return charArray;
    }
}