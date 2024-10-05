package ru.liga.consoleParcels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Класс DTO для запроса на посылку.
 *
 * <p>
 * Этот класс представляет данные, необходимые для запроса на посылку.
 * Он включает информацию о имени посылки, форме посылки и символе, представляющем посылку.
 * Класс также предоставляет метод для проверки наличия в форме посылки символов, отличных от указанного.
 * </p>
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Log4j2
public class ParcelRequestDto {
    private String name;
    private String shape;
    private char symbol;

    /**
     * Метод для проверки наличия в форме посылки символов, отличных от указанного.
     *
     * @return true, если в форме посылки есть символы, отличные от указанного символа и пробела, иначе false.
     */
    public boolean isThereSymbolThatIsNotSpecified() {
        for (char c : shape.toCharArray()) {
            log.info("Проверка символа {}", c);
            if (c != symbol && c != ' ') {
                return true;
            }
        }
        return false;
    }

    /**
     * Переопределение метода toString для представления объекта в виде строки.
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return "ParcelRequestDto{" +
                "name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                ", symbol=" + symbol +
                '}';
    }
}