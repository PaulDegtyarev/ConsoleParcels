package ru.liga.console_parcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.exception.FileNotFoundException;
import ru.liga.console_parcels.service.PackageReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса для чтения посылок из файла.
 */
@Log4j2
@Service
public class DefaultPackageReader implements PackageReader {
    private static final String EMPTY_LINE = "";

    /**
     * Читает посылки из файла.
     *
     * @param filename Имя файла с данными посылок.
     * @return Список посылок для упаковки.
     */
    @Override
    public List<ParcelForPackagingDto> readPackages(String filename) {
        log.info("Начало чтения посылок из файла: {}", filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return readParcelsFromFile(reader);
        } catch (IOException e) {
            throw new FileNotFoundException(String.format("Файл %s не найден", filename));
        }
    }

    private List<ParcelForPackagingDto> readParcelsFromFile(BufferedReader reader) throws IOException {
        int defaultLength = 0;
        List<ParcelForPackagingDto> parcels = new ArrayList<>();
        StringBuilder parcelData = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().equals(EMPTY_LINE)) {
                if (!parcelData.isEmpty()) {
                    parcels.add(createParcel(parcelData.toString()
                            .trim()
                            .split("\n")));
                    parcelData.setLength(defaultLength);
                }
            } else {
                parcelData.append(line).append("\n");
            }
        }

        if (!parcelData.isEmpty()) {
            parcels.add(createParcel(parcelData.toString()
                    .trim()
                    .split("\n")));
        }

        return parcels;
    }

    private ParcelForPackagingDto createParcel(String[] lines) {
        int firstRowIndex = 0;
        char[][] shape = parseParcelShape(lines);
        return new ParcelForPackagingDto(shape.length, shape[firstRowIndex].length, shape);
    }

    private char[][] parseParcelShape(String[] lines) {
        int indexOfFirstElement = 0;
        int height = lines.length;
        int width = lines[indexOfFirstElement].length();
        char[][] shape = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                shape[i][j] = lines[i].charAt(j);
            }
        }
        return shape;
    }
}