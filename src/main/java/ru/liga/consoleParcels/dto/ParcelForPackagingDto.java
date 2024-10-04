package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParcelForPackagingDto {
    private final int height;
    private final int width;
    private char[][] shape;


    public int getArea() {
        return width * height;
    }
}
