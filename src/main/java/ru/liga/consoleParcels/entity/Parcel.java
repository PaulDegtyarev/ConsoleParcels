package ru.liga.consoleParcels.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Класс для представления посылки.
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "parcels", schema = "parcels")
public class Parcel {
    @Id
    @Column(name = "name")
    private final String name;

    @Column(name = "shape")
    private char[][] shape;

    @Column(name = "symbol")
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