package ru.liga.consoleParcels.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.exception.PackageShapeException;
import ru.liga.consoleParcels.model.Parcel;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.service.PackageReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Сервис для чтения данных о посылках из файла.
 * <p>
 * Этот класс считывает данные о посылках из текстового файла,
 * где каждая посылка представлена набором строк,
 * описывающих ее форму. Затем он создает объекты {@link Parcel}
 * на основе этих данных и возвращает их в виде списка.
 */
@Log4j2
@Service
public class DefaultPackageReader implements PackageReader {
    /**
     * Считывает данные о посылках из файла.
     * <p>
     * Считывает данные из файла построчно, формируя данные о
     * форме посылки. Затем проверяет, является ли форма
     * допустимой, и создает объект {@link Parcel}, если форма
     * допустима.
     *
     * @param filename Имя файла, из которого нужно считать данные.
     * @return Список объектов {@link Parcel}, представляющих
     * считанные посылки.
     * @throws FileNotFoundException Если файл не найден.
     * @throws PackageShapeException Если форма одной из посылок
     *                               недопустима.
     */
    @Override
    public List<ParcelForPackagingDto> readPackages(String filename) {
        List<ParcelForPackagingDto> parcelsForPackaging = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder parcelData = new StringBuilder();
            String line;

            String[] split = parcelData.toString().trim().split("\n");
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (!parcelData.isEmpty()) {
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
                        parcelData.setLength(0);
                    }
                } else {
                    parcelData.append(line).append("\n");
                }
            }

            if (!parcelData.isEmpty()) {
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

        } catch (IOException ioException) {
            throw new FileNotFoundException(String.format("Файл %s не найден", filename));
        }

        return parcelsForPackaging;
    }
}