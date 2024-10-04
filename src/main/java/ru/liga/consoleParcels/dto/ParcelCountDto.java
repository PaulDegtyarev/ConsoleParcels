package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelCountDto {
    private String form;
    private int quantity;
}
