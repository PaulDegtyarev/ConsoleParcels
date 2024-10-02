package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Log4j2
public class ParcelRequestDto {
    private String name;
    private String shape;
    private char symbol;

    public boolean isEachCharacterSpecified() {
        for (char c : shape.toCharArray()) {
            log.info("Проверка символа {}", c);
            if (c != symbol && c != ' ') {
                return true;
            }
        }
        return false;
    }
}

//add-parcel Ноут "1 1" 1