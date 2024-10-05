package ru.liga.consoleParcels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.service.ParcelService;

/**
 * Контроллер для управления операциями с посылками.
 *
 * <p>
 * Этот контроллер отвечает за выполнение различных операций с посылками,
 * таких как поиск, добавление, обновление и удаление. Он использует сервис
 * {@link ParcelService} для выполнения этих операций.
 * </p>
 *
 * <p>
 * Контроллер интегрирован с Spring Shell, что позволяет вызывать его методы
 * через командную строку.
 * </p>
 */
@ShellComponent
public class ParcelController {
    private ParcelService parcelService;

    /**
     * Конструктор класса ParcelController.
     *
     * @param parcelService Сервис для управления посылками.
     */
    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    /**
     * Метод для получения информации обо всех посылках.
     *
     * @return Строка с информацией обо всех посылках.
     */
    @ShellMethod
    public String findAllParcels() {
        return parcelService.findAllParcels();
    }

    /**
     * Метод для поиска посылки по имени.
     *
     * @param name Имя посылки.
     * @return Объект ParcelResponseDto, содержащий информацию о найденной посылке.
     */
    @ShellMethod
    public ParcelResponseDto findParcelByName(String name) {
        return parcelService.findParcelByName(name);
    }

    /**
     * Метод для добавления новой посылки.
     *
     * @param name   Имя посылки.
     * @param shape  Форма посылки.
     * @param symbol Символ, представляющий посылку.
     * @return Объект добавленной посылки (DTO).
     */
    @ShellMethod
    public ParcelResponseDto addParcel(String name, String shape, char symbol) {
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);
        return parcelService.addParcel(parcelRequestDto);
    }

    /**
     * Метод для обновления посылки по имени.
     *
     * @param nameOfSavedParcel Имя существующей посылки.
     * @param newShape          Новая форма посылки.
     * @param newSymbol         Новый символ, представляющий посылку.
     * @return Объект обновленной посылки (DTO).
     */
    @ShellMethod
    public ParcelResponseDto updateParcelByParcelName(String nameOfSavedParcel, String newShape, char newSymbol) {
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(nameOfSavedParcel, newShape, newSymbol);
        return parcelService.updateParcelByName(parcelRequestDto);
    }

    /**
     * Метод для обновления символа посылки по имени.
     *
     * @param nameOfSavedParcel Имя существующей посылки.
     * @param newSymbol         Новый символ, представляющий посылку.
     * @return Объект обновленной посылки (DTO).
     */
    @ShellMethod
    public ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol) {
        return parcelService.updateSymbolByParcelName(nameOfSavedParcel, newSymbol);
    }

    /**
     * Метод для обновления формы посылки по имени.
     *
     * @param nameOfSavedParcel Имя существующей посылки.
     * @param shape             Новая форма посылки.
     * @return Объект обновленной посылки (DTO).
     */
    @ShellMethod
    public ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String shape) {
        return parcelService.updateShapeByParcelName(nameOfSavedParcel, shape);
    }

    /**
     * Метод для удаления посылки по имени.
     *
     * @param nameOfParcelForDelete Имя посылки, которую необходимо удалить.
     */
    @ShellMethod
    public void deleteParcelByParcelName(String nameOfParcelForDelete) {
        parcelService.deleteParcelByParcelName(nameOfParcelForDelete);
    }
}
