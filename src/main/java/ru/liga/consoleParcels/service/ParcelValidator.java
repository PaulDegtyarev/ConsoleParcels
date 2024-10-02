package ru.liga.consoleParcels.service;

public interface ParcelValidator {
    void validateParcelShape(String shape);

    void validateParcelSymbol(char symbol);
}
