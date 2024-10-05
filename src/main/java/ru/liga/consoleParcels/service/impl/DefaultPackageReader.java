package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.service.PackageReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация сервиса для чтения посылок из файла.
 */
@Log4j2
@Service
public class DefaultPackageReader implements PackageReader {
    /**
     * Читает посылки из файла.
     *
     * @param filename Имя файла с данными посылок.
     * @return Список посылок для упаковки.
     */
    @Override
    public List<ParcelForPackagingDto> readPackages(String filename) {
        log.info("Начало чтения посылок из файла: {}", filename);
        List<ParcelForPackagingDto> parcelsForPackaging = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            log.debug("Файл {} успешно открыт для чтения", filename);
            StringBuilder parcelData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                log.trace("Чтение строки {}", line);
                if (line.trim().isEmpty()) {
                    if (!parcelData.isEmpty()) {
                        String[] split = parcelData.toString().trim().split("\n");
                        int height = split.length;
                        int width = split[0].length();

                        char[][] shape = new char[height][width];
                        for (int i = 0; i < height; i++) {
                            for (int j = 0; j < width; j++) {
                                shape[i][j] = split[i].charAt(j);
                            }
                        }

                        ParcelForPackagingDto parcel = new ParcelForPackagingDto(height, width, shape);
                        parcelsForPackaging.add(parcel);
                        log.info("Посылка {} добавлена в список. Размер: {}x{}", parcelsForPackaging.size(), parcel.getHeight(), parcel.getWidth());
                        log.debug("Форма посылки {}: {}", parcelsForPackaging.size(), Arrays.deepToString(parcel.getShape()));
                        parcelData.setLength(0);
                    }
                } else {
                    parcelData.append(line).append("\n");
                }
            }

            if (!parcelData.isEmpty()) {
                String[] split = parcelData.toString().trim().split("\n");
                int height = split.length;
                int width = split[0].length();

                char[][] shape = new char[height][width];
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        shape[i][j] = split[i].charAt(j);
                    }
                }
                parcelsForPackaging.add(new ParcelForPackagingDto(height, width, shape));
                log.info("Посылка с формой {} добавлена в список", Arrays.deepToString(shape));
            }

            log.info("Чтение файла завершено. Всего прочитано {} посылок", parcelsForPackaging.size());

        } catch (IOException ioException) {
            throw new FileNotFoundException(String.format("Файл %s не найден", filename));
        }

        return parcelsForPackaging;
    }
}