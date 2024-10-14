package ru.liga.cargomanagement.service;

import ru.liga.cargomanagement.dto.ParcelRequestDto;
import ru.liga.cargomanagement.dto.ParcelResponseDto;

import java.util.List;

/**
 * Интерфейс для управления посылками.
 */
public interface ParcelService {

    /**
     * Возвращает список всех посылок.
     *
     * @return Список DTO всех посылок.
     */
    List<ParcelResponseDto> findAllParcels();

    /**
     * Находит посылку по названии.
     *
     * @param name название посылки.
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
     * Обновляет посылку по названию.
     *
     * @param parcelRequestDto Данные для обновления посылки.
     * @return DTO с информацией об обновленной посылке.
     */
    ParcelResponseDto updateByName(ParcelRequestDto parcelRequestDto);

    /**
     * Обновляет символ посылки по названию.
     *
     * @param nameOfSavedParcel Название посылки.
     * @param newSymbol         Новый символ для посылки.
     * @return DTO с информацией об обновленной посылке.
     */
    ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol);

    /**
     * Обновляет форму посылки по названию.
     *
     * @param nameOfSavedParcel Название посылки.
     * @param newShape          Новая форма для посылки.
     * @return DTO с информацией об обновленной посылке.
     */
    ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String newShape);

    /**
     * Удаляет посылку по названию.
     *
     * @param nameOfParcelForDelete Имя посылки для удаления.
     */
    void deleteParcelByParcelName(String nameOfParcelForDelete);
}
