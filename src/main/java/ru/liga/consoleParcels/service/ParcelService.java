package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;

import java.util.List;

/**
 * Интерфейс для управления посылками.
 */
public interface ParcelService {

    /**
     * Возвращает список всех посылок.
     *
     * @return Строка с информацией о всех посылках.
     */
    List<ParcelResponseDto> findAllParcels();

    /**
     * Находит посылку по имени.
     *
     * @param name Имя посылки.
     * @return DTO с информацией о посылке.
     */
    ParcelResponseDto findParcelByName(String name);

    /**
     * Добавляет новую посылку.
     *
     * @param parcelRequestDto Данные для создания новой посылки.
     * @return DTO с информацией о созданной посылке.
     */
    ParcelResponseDto addParcel(ParcelRequestDto parcelRequestDto);

    /**
     * Обновляет посылку по имени.
     *
     * @param parcelRequestDto Данные для обновления посылки.
     * @return DTO с информацией об обновленной посылке.
     */
    ParcelResponseDto updateParcelByName(ParcelRequestDto parcelRequestDto);

    /**
     * Обновляет символ посылки по имени.
     *
     * @param nameOfSavedParcel Имя посылки.
     * @param newSymbol         Новый символ для посылки.
     * @return DTO с информацией об обновленной посылке.
     */
    ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol);

    /**
     * Обновляет форму посылки по имени.
     *
     * @param nameOfSavedParcel Имя посылки.
     * @param newShape          Новая форма для посылки.
     * @return DTO с информацией об обновленной посылке.
     */
    ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String newShape);

    /**
     * Удаляет посылку по имени.
     *
     * @param nameOfParcelForDelete Имя посылки для удаления.
     */
    void deleteParcelByParcelName(String nameOfParcelForDelete);
}
