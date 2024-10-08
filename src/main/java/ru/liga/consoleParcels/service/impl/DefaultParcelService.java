package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.exception.ParcelNameConflictException;
import ru.liga.consoleParcels.exception.ParcelNotFoundException;
import ru.liga.consoleParcels.exception.WrongSymbolInShapeException;
import ru.liga.consoleParcels.factory.ParcelServiceResponseFactory;
import ru.liga.consoleParcels.entity.Parcel;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.ParcelService;
import ru.liga.consoleParcels.service.ParcelValidator;

import java.util.List;

/**
 * Реализация сервиса для управления посылками.
 */
@Service
@Log4j2
public class DefaultParcelService implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final ParcelServiceResponseFactory parcelServiceResponseFactory;
    private final ParcelValidator parcelValidator;

    /**
     * Конструктор с зависимостями.
     *
     * @param parcelRepository             Репозиторий посылок.
     * @param parcelServiceResponseFactory Фабрика ответов сервиса посылок.
     * @param parcelValidator              Валидатор посылок.
     */
    @Autowired
    public DefaultParcelService(ParcelRepository parcelRepository, ParcelServiceResponseFactory parcelServiceResponseFactory, ParcelValidator parcelValidator) {
        this.parcelRepository = parcelRepository;
        this.parcelServiceResponseFactory = parcelServiceResponseFactory;
        this.parcelValidator = parcelValidator;
    }

    /**
     * Возвращает список всех посылок.
     *
     * @return Строка с информацией о всех посылках.
     */
    @Override
    public List<ParcelResponseDto> findAllParcels() {
        log.info("Получение списка всех посылок");
        return parcelRepository.findAll()
                .stream()
                .map(parcelServiceResponseFactory::createServiceResponse)
                .toList();
    }

    /**
     * Находит посылку по имени.
     *
     * @param name Имя посылки.
     * @return DTO с информацией о посылке.
     * @throws ParcelNotFoundException Если посылка не найдена.
     */
    @Override
    public ParcelResponseDto findParcelByName(String name) {
        log.info("Начинается поиск посылки с названием: {}", name);
        return parcelRepository.findParcelByName(name.trim().toLowerCase())
                .map(parcelServiceResponseFactory::createServiceResponse)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием: " + name + " не найдена"));
    }

    /**
     * Добавляет новую посылку.
     *
     * @param parcelRequestDto Данные для создания новой посылки.
     * @return DTO с информацией о созданной посылке.
     * @throws ParcelNameConflictException Если посылка с таким именем уже существует.
     * @throws WrongSymbolInShapeException Если форма посылки содержит недопустимые символы.
     */
    @Override
    public ParcelResponseDto addParcel(ParcelRequestDto parcelRequestDto) {
        log.info("Начинается добавление посылки с данными: {}", parcelRequestDto);
        String name = parcelRequestDto.getName();

        String shape = parcelRequestDto.getShape();
        parcelValidator.validateParcelShape(shape);

        char symbol = parcelRequestDto.getSymbol();
        parcelValidator.validateParcelSymbol(symbol);

        if (parcelRequestDto.isThereSymbolThatIsNotSpecified()) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + symbol);
        }

        String trimmedName = name.trim()
                .toLowerCase();
        if (parcelRepository.existsByName(trimmedName)) {
            throw new ParcelNameConflictException("Посылка с названием " + name + " уже существует");
        }

        shape = shape.replace(" ", "\n");
        Parcel newParcel = new Parcel(trimmedName, shape, symbol);
        parcelRepository.save(newParcel);

        log.info("Посылка успешно добавлена: {}", newParcel);
        return parcelServiceResponseFactory.createServiceResponse(newParcel);
    }

    /**
     * Обновляет посылку по имени.
     *
     * @param parcelRequestDto Данные для обновления посылки.
     * @return DTO с информацией о обновленной посылке.
     * @throws ParcelNotFoundException     Если посылка не найдена.
     * @throws WrongSymbolInShapeException Если форма посылки содержит недопустимые символы.
     */
    @Override
    public ParcelResponseDto updateParcelByName(ParcelRequestDto parcelRequestDto) {
        log.info("Начинается обновление посылки с данными: {}", parcelRequestDto);
        String nameOfSavedParcel = parcelRequestDto.getName();

        String shape = parcelRequestDto.getShape();
        parcelValidator.validateParcelShape(shape);

        char newSymbol = parcelRequestDto.getSymbol();
        parcelValidator.validateParcelSymbol(newSymbol);

        if (parcelRequestDto.isThereSymbolThatIsNotSpecified()) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + newSymbol);
        }

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelToUpdate = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        shape = shape.replace(" ", "\n");
        parcelToUpdate.updateShapeWithNewSymbol(shape, newSymbol);
        parcelRepository.save(parcelToUpdate);

        log.info("Посылка успешно обновлена: {}", parcelToUpdate);
        return parcelServiceResponseFactory.createServiceResponse(parcelToUpdate);
    }

    /**
     * Обновляет символ посылки по имени.
     *
     * @param nameOfSavedParcel Имя посылки.
     * @param newSymbol         Новый символ для посылки.
     * @return DTO с информацией о обновленной посылке.
     * @throws ParcelNotFoundException Если посылка не найдена.
     */
    @Override
    public ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol) {
        log.info("Начинается обновление символа посылки с названием: {}, новый символ: {}", nameOfSavedParcel, newSymbol);
        parcelValidator.validateParcelSymbol(newSymbol);

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateSymbol = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        String shape = parcelWithUpdateSymbol.getShape();
        char oldSymbol = parcelWithUpdateSymbol.getSymbol();
        shape = shape.replace(oldSymbol, newSymbol);

        parcelWithUpdateSymbol.updateShapeWithNewSymbol(shape, newSymbol);
        parcelRepository.save(parcelWithUpdateSymbol);

        log.info("Символ посылки успешно обновлен: {}", parcelWithUpdateSymbol);
        return parcelServiceResponseFactory.createServiceResponse(parcelWithUpdateSymbol);
    }

    /**
     * Обновляет форму посылки по имени.
     *
     * @param nameOfSavedParcel Имя посылки.
     * @param newShape          Новая форма для посылки.
     * @return DTO с информацией о обновленной посылке.
     * @throws ParcelNotFoundException Если посылка не найдена.
     */
    @Override
    public ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String newShape) {
        log.info("Начинается обновление формы посылки с названием: {}, новая форма: {}", nameOfSavedParcel, newShape);
        parcelValidator.validateParcelShape(newShape);

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateShape = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        newShape = newShape.replace(" ", "\n");
        parcelWithUpdateShape.updateShape(newShape);
        parcelRepository.save(parcelWithUpdateShape);

        log.info("Форма посылки успешно обновлена: {}", parcelWithUpdateShape);
        return parcelServiceResponseFactory.createServiceResponse(parcelWithUpdateShape);
    }

    /**
     * Удаляет посылку по имени.
     *
     * @param nameOfParcelForDelete Имя посылки для удаления.
     */
    @Override
    public void deleteParcelByParcelName(String nameOfParcelForDelete) {
        log.info("Начинается удаление посылки с названием: {}", nameOfParcelForDelete);
        String trimmedNameOfSavedParcel = nameOfParcelForDelete.trim().toLowerCase();

        parcelRepository.deleteById(trimmedNameOfSavedParcel);
        log.info("Посылка с названием: {} успешно удалена", nameOfParcelForDelete);
    }
}