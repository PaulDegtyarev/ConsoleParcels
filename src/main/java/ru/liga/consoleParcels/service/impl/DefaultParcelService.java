package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelDto;
import ru.liga.consoleParcels.exception.ParcelNameConflictException;
import ru.liga.consoleParcels.exception.ParcelNotFoundException;
import ru.liga.consoleParcels.exception.WrongSymbolInShapeException;
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
                .map(parcel -> new ParcelDto(
                        parcel.getName(),
                        parcel.getShape(),
                        parcel.getSymbol()
                ))
                .map(ParcelDto::toString)
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public ParcelDto findParcelByName(String name) {
        log.info("Начинается поиск посылки с названием: {}", name);
        return parcelRepository.findParcelByName(name.trim().toLowerCase())
                .map(parcel -> new ParcelDto(
                            parcel.getName(),
                            parcel.getShape(),
                            parcel.getSymbol()
                ))
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием: " + name + " не найдена"));
    }

    @Override
    public ParcelDto addParcel(String name, String shape, char symbol) {
        String trimmedName = name.trim().toLowerCase();

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

        return new ParcelDto(
                newParcel.getName(),
                newParcel.getShape(),
                newParcel.getSymbol()
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
