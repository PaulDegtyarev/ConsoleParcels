package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.factory.ParcelServiceResponseFactory;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.ParcelService;

import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultParcelService implements ParcelService {
    private ParcelRepository parcelRepository;
    private ParcelServiceResponseFactory parcelServiceResponseFactory;

    @Autowired
    public DefaultParcelService(ParcelRepository parcelRepository, ParcelServiceResponseFactory parcelServiceResponseFactory) {
        this.parcelRepository = parcelRepository;
        this.parcelServiceResponseFactory = parcelServiceResponseFactory;
    }

    @Override
    public String findAllParcels() {
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
    public ParcelResponseDto addParcel(ParcelRequestDto parcelRequest) {
        String name = parcelRequest.getName();

        String shape = parcelRequest.getShape();
        if (shape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }

        char symbol = parcelRequest.getSymbol();
        if (symbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }

        if (parcelRequest.isEachCharacterSpecified()) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + symbol);
        }

        String trimmedName = name.trim()
                .toLowerCase();
        if (parcelRepository.existsByName(trimmedName)) {
            throw new ParcelNameConflictException("Посылка с названием " + name + " уже существует");
        }

        char[][] shapeCharArray = parseShape(shape);
        Parcel newParcel = new Parcel(trimmedName, shapeCharArray, symbol);
        parcelRepository.save(newParcel);

        return parcelServiceResponseFactory.createServiceResponse(newParcel);
    }

    @Override
    public ParcelResponseDto updateParcelByName(ParcelRequestDto parcelRequest) {
        String nameOfSavedParcel = parcelRequest.getName();

        String shape = parcelRequest.getShape();
        if (shape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }

        char newSymbol = parcelRequest.getSymbol();
        if (newSymbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }

        if (parcelRequest.isEachCharacterSpecified()) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + newSymbol);
        }

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelToUpdate = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        char[][] newShapeCharArray = parseShape(shape);
        parcelToUpdate.updateShapeWithNewSymbol(newShapeCharArray, newSymbol);
        parcelRepository.save(parcelToUpdate);

        return parcelServiceResponseFactory.createServiceResponse(parcelToUpdate);
    }

    @Override
    public ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol) {
        if (newSymbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateSymbol = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        char[][] newShape = updateSavedShapeWithNewSymbol(parcelWithUpdateSymbol.getShape(), newSymbol);
        parcelWithUpdateSymbol.updateShapeWithNewSymbol(newShape, newSymbol);
        parcelRepository.save(parcelWithUpdateSymbol);

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
        if (newShape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateShape = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        char[][] parsedShape = parseShape(newShape);
        parcelWithUpdateShape.updateShape(parsedShape);
        parcelRepository.save(parcelWithUpdateShape);

        return parcelServiceResponseFactory.createServiceResponse(parcelWithUpdateShape);
    }

    private char[][] parseShape(String shape) {
        String[] lines = shape.split(" ");
        int height = lines.length;
        int maxWidth = 0;

        for (String line : lines) {
            maxWidth = Math.max(maxWidth, line.length());
        }

        char[][] shapeArray = new char[height][maxWidth];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                shapeArray[i][j] = lines[i].charAt(j);
            }
            for (int j = lines[i].length(); j < maxWidth; j++) {
                shapeArray[i][j] = ' ';
            }
        }

        return shapeArray;
    }

    @Override
    public void deleteParcelByParcelName(String nameOfParcelForDelete) {
        String trimmedNameOfSavedParcel = nameOfParcelForDelete.trim().toLowerCase();

        parcelRepository.deleteParcelByParcelName(trimmedNameOfSavedParcel);
    }
}
