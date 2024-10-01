package ru.liga.consoleParcels.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Представляет посылку с определенными характеристиками.
 * <p>
 * Посылка имеет высоту, ширину, идентификатор и форму,
 * которая представлена двумерным массивом символов.
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Parcel {
    private final String name;
    private char[][] shape;
    private char symbol;

    public void updateShapeWithNewSymbol(char[][] newShape, char newSymbol) {
        this.shape = newShape;
        this.symbol = newSymbol;
    }
}