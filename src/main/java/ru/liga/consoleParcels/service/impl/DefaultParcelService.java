package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.ParcelService;

import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultParcelService implements ParcelService {
    private ParcelRepository parcelRepository;

    @Autowired
    public DefaultParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public String findAllParcels() {
        return parcelRepository.findAll()
                .stream()
                .map(parcel -> new ParcelResponseDto(
                        parcel.getName(),
                        parcel.getShape(),
                        parcel.getSymbol()
                ))
                .map(ParcelResponseDto::toString)
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public ParcelResponseDto findParcelByName(String name) {
        log.info("Начинается поиск посылки с названием: {}", name);
        return parcelRepository.findParcelByName(name.trim().toLowerCase())
                .map(parcel -> new ParcelResponseDto(
                            parcel.getName(),
                            parcel.getShape(),
                            parcel.getSymbol()
                ))
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием: " + name + " не найдена"));
    }

    @Override
    public ParcelResponseDto addParcel(ParcelRequestDto parcelRequest) {
        String name = parcelRequest.getName();
        String shape = parcelRequest.getShape();
        char symbol = parcelRequest.getSymbol();
        String trimmedName = parcelRequest.getName()
                .trim()
                .toLowerCase();

        boolean areAnyCharsNotSymbol = shape.chars()
                .mapToObj(c -> (char) c)
                .anyMatch(ch -> ch != symbol);

        if (areAnyCharsNotSymbol) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + symbol);
        }

        if (parcelRepository.existsByName(trimmedName)) {
            throw new ParcelNameConflictException("Посылка с названием " + name + " уже существует");
        }

        char[][] shapeCharArray = parseShape(shape);

        Parcel newParcel = new Parcel(name, shapeCharArray, symbol);
        parcelRepository.save(newParcel);

        return new ParcelResponseDto(
                newParcel.getName(),
                newParcel.getShape(),
                newParcel.getSymbol()
        );
    }

    @Override
    public ParcelResponseDto updateParcelByName(ParcelRequestDto parcelRequest) {
        String nameOfSavedParcel = parcelRequest.getName();
        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        String shape = parcelRequest.getShape();
        char newSymbol = parcelRequest.getSymbol();

        boolean areAnyCharsNotSymbol = shape.chars()
                .mapToObj(c -> (char) c)
                .anyMatch(ch -> ch != newSymbol);

        if (areAnyCharsNotSymbol) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + newSymbol);
        }

        char[][] newShapeCharArray = parseShape(shape);

        Parcel parcelToUpdate = parcelRepository.findParcelByName(trimmedNameOfSavedParcel).orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));
        parcelToUpdate.updateShapeWithNewSymbol(newShapeCharArray, newSymbol);
        parcelRepository.save(parcelToUpdate);

        return new ParcelResponseDto(
                parcelToUpdate.getName(),
                parcelToUpdate.getShape(),
                parcelToUpdate.getSymbol()
        );
    }

    @Override
    public ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol) {
        if (newSymbol == ' ') {
            throw new InvalidCharacterException("Нельзя сделать символ пробелом");
        }

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();

        Parcel parcelWithUpdateSymbol = parcelRepository.findParcelByName(trimmedNameOfSavedParcel).orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));
        char[][] currentShape = parcelWithUpdateSymbol.getShape();

        char[][] newShape = new char[currentShape.length][];
        for (int i = 0; i < currentShape.length; i++) {
            newShape[i] = new char[currentShape[i].length];
            for (int j = 0; j < currentShape[i].length; j++) {
                newShape[i][j] = newSymbol;
            }
        }

        parcelWithUpdateSymbol.updateShapeWithNewSymbol(newShape, newSymbol);
        parcelRepository.save(parcelWithUpdateSymbol);

        return new ParcelResponseDto(
                parcelWithUpdateSymbol.getName(),
                parcelWithUpdateSymbol.getShape(),
                parcelWithUpdateSymbol.getSymbol()
        );
    }

    @Override
    public ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String newShape) {
        if (newShape.isBlank()) {
            throw new InvalidShapeException("Новая форма не может быть пробелами");
        }

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateShape = parcelRepository.findParcelByName(trimmedNameOfSavedParcel).orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        char[][] parsedShape = parseShape(newShape);

        parcelWithUpdateShape.updateShape(parsedShape);
        parcelRepository.save(parcelWithUpdateShape);

        return new ParcelResponseDto(
                parcelWithUpdateShape.getName(),
                parcelWithUpdateShape.getShape(),
                parcelWithUpdateShape.getSymbol()
        );
    }

    private char[][] parseShape(String shape) {
        String[] lines = shape.split("\\n");
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
}
