package ru.liga.console_parcels.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.console_parcels.dto.ParcelRequestDto;
import ru.liga.console_parcels.dto.ParcelResponseDto;
import ru.liga.console_parcels.service.ParcelService;

import java.util.stream.Collectors;

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
@Log4j2
@RequiredArgsConstructor
public class ParcelShellController {
    private final ParcelService parcelService;

    /**
     * Метод для получения информации обо всех посылках.
     *
     * @return Строка с информацией обо всех посылках.
     */
    @ShellMethod
    public String findAll() {
        log.info("Пользователь выбрал достать все посылки.");
        return parcelService.findAllParcels()
                .stream()
                .map(ParcelResponseDto::toString)
                .collect(Collectors.joining("\n\n"));
    }

    /**
     * Метод для поиска посылки по имени.
     *
     * @param name Имя посылки.
     * @return Объект ParcelResponseDto, содержащий информацию о найденной посылке.
     */
    @ShellMethod
    public ParcelResponseDto findByName(String name) {
        log.info("Пользователь выбрал достать посылку по названию.");
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
    public ParcelResponseDto add(String name, String shape, char symbol) {
        log.info("Пользователь выбрал добавить посылку.");
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
    public ParcelResponseDto updateByName(String nameOfSavedParcel, String newShape, char newSymbol) {
        log.info("Пользователь выбрал обновить посылку по ее названию.");
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
    public ParcelResponseDto updateSymbolByName(String nameOfSavedParcel, char newSymbol) {
        log.info("Пользователь выбрал обновить символ посылки.");
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
    public ParcelResponseDto updateShapeByName(String nameOfSavedParcel, String shape) {
        log.info("Пользователь выбрал обновить форму посылки.");
        return parcelService.updateShapeByParcelName(nameOfSavedParcel, shape);
    }

    /**
     * Метод для удаления посылки по имени.
     *
     * @param nameOfParcelForDelete Имя посылки, которую необходимо удалить.
     */
    @ShellMethod
    public void deleteByName(String nameOfParcelForDelete) {
        log.info("Пользователь выбрал удалить посылку.");
        parcelService.deleteParcelByParcelName(nameOfParcelForDelete);
    }
}