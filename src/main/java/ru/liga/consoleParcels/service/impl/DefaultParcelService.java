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
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.ParcelService;
import ru.liga.consoleParcels.service.ParcelValidator;
import ru.liga.consoleParcels.service.ShapeParser;

import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultParcelService implements ParcelService {
    private ParcelRepository parcelRepository;
    private ParcelServiceResponseFactory parcelServiceResponseFactory;
    private ParcelValidator parcelValidator;
    private ShapeParser shapeParser;

    @Autowired
    public DefaultParcelService(ParcelRepository parcelRepository, ParcelServiceResponseFactory parcelServiceResponseFactory, ParcelValidator parcelValidator, ShapeParser shapeParser) {
        this.parcelRepository = parcelRepository;
        this.parcelServiceResponseFactory = parcelServiceResponseFactory;
        this.parcelValidator = parcelValidator;
        this.shapeParser = shapeParser;
    }

    @Override
    public String findAllParcels() {
        log.info("Получение списка всех посылок");
        return parcelRepository.findAll()
                .stream()
                .map(parcelServiceResponseFactory::createServiceResponse)
                .map(ParcelResponseDto::toString)
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public ParcelResponseDto findParcelByName(String name) {
        log.info("Начинается поиск посылки с названием: {}", name);
        return parcelRepository.findParcelByName(name.trim().toLowerCase())
                .map(parcelServiceResponseFactory::createServiceResponse)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием: " + name + " не найдена"));
    }

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

        char[][] shapeCharArray = shapeParser.parseShape(shape);
        Parcel newParcel = new Parcel(trimmedName, shapeCharArray, symbol);
        parcelRepository.save(newParcel);

        log.info("Посылка успешно добавлена: {}", newParcel);
        return parcelServiceResponseFactory.createServiceResponse(newParcel);
    }

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

        char[][] newShapeCharArray = shapeParser.parseShape(shape);
        parcelToUpdate.updateShapeWithNewSymbol(newShapeCharArray, newSymbol);
        parcelRepository.save(parcelToUpdate);

        log.info("Посылка успешно обновлена: {}", parcelToUpdate);
        return parcelServiceResponseFactory.createServiceResponse(parcelToUpdate);
    }

    @Override
    public ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol) {
        log.info("Начинается обновление символа посылки с названием: {}, новый символ: {}", nameOfSavedParcel, newSymbol);
        parcelValidator.validateParcelSymbol(newSymbol);

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateSymbol = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        char[][] newShape = updateSavedShapeWithNewSymbol(parcelWithUpdateSymbol.getShape(), newSymbol);
        parcelWithUpdateSymbol.updateShapeWithNewSymbol(newShape, newSymbol);
        parcelRepository.save(parcelWithUpdateSymbol);

        log.info("Символ посылки успешно обновлен: {}", parcelWithUpdateSymbol);
        return parcelServiceResponseFactory.createServiceResponse(parcelWithUpdateSymbol);
    }

    private char[][] updateSavedShapeWithNewSymbol(char[][] shapeOfSavedPassword, char newSymbol) {
        char[][] newShape = new char[shapeOfSavedPassword.length][];
        for (int i = 0; i < shapeOfSavedPassword.length; i++) {
            newShape[i] = new char[shapeOfSavedPassword[i].length];
            for (int j = 0; j < shapeOfSavedPassword[i].length; j++) {
                newShape[i][j] = newSymbol;
            }
        }

        return newShape;
    }

    @Override
    public ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String newShape) {
        log.info("Начинается обновление формы посылки с названием: {}, новая форма: {}", nameOfSavedParcel, newShape);
        parcelValidator.validateParcelShape(newShape);

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateShape = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        char[][] parsedShape = shapeParser.parseShape(newShape);
        parcelWithUpdateShape.updateShape(parsedShape);
        parcelRepository.save(parcelWithUpdateShape);

        log.info("Форма посылки успешно обновлена: {}", parcelWithUpdateShape);
        return parcelServiceResponseFactory.createServiceResponse(parcelWithUpdateShape);
    }

    @Override
    public void deleteParcelByParcelName(String nameOfParcelForDelete) {
        log.info("Начинается удаление посылки с названием: {}", nameOfParcelForDelete);
        String trimmedNameOfSavedParcel = nameOfParcelForDelete.trim().toLowerCase();

        parcelRepository.deleteParcelByParcelName(trimmedNameOfSavedParcel);
        log.info("Посылка с названием: {} успешно удалена", nameOfParcelForDelete);
    }
}