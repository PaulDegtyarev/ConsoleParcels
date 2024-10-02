package ru.liga.consoleParcels.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.exception.PackageShapeException;
import ru.liga.consoleParcels.mapper.ParcelMapper;
import ru.liga.consoleParcels.model.Parcel;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.service.PackageReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    public List<ParcelMapper> readPackages(String filename) {
        List<ParcelMapper> parcels = new ArrayList<>();
        int firstSymbolPosition = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder parcelData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (!parcelData.isEmpty()) {
                        String shape = parcelData.toString().trim();
                        char symbol = shape.charAt(firstSymbolPosition);

                        parcels.add(new ParcelMapper(shape));
                        log.info("Посылка с формой {} добавлена в список", shape);

                        parcelData.setLength(0);
                    }
                } else {
                    parcelData.append(line).append("\n");
                }
            }

            if (!parcelData.isEmpty()) {
                String shape = parcelData.toString().trim();
                char symbol = shape.charAt(firstSymbolPosition);

                parcels.add(new ParcelMapper(shape));
                log.info("Посылка с формой {} добавлена в список", shape);
            }

        } catch (IOException ioException) {
            throw new FileNotFoundException(String.format("Файл %s не найден", filename));
        }

        return parcels;
    }
}