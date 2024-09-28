package ru.liga.consoleParcels.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.exception.PackageShapeException;
import ru.liga.consoleParcels.model.Parcel;
import lombok.extern.log4j.Log4j2;

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
public class PackageReader {
    private static final String[] ALLOWED_PARCELS = {
            "1",
            "22",
            "333",
            "4444",
            "55555",
            "666\n666",
            "777\n7777",
            "8888\n8888",
            "999\n999\n999"
    };

    private boolean isValidParcel(String input) {
        for (String allowedParcel : ALLOWED_PARCELS) {
            if (input.trim().equals(allowedParcel.trim())) {
                return true;
            }
        }

        return false;
    }

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
    public List<Parcel> readPackages(String filename) {
        List<Parcel> parcels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder parcelData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (!parcelData.isEmpty()) {
                        String shape = parcelData.toString().trim();

                        if (isValidParcel(shape)) {
                            parcels.add(new Parcel(shape));
                            log.info("Посылка с формой {} добавлена в список", shape);
                        } else {
                            throw new PackageShapeException(String.format("Неверная форма посылки: %s, из файла: %s", shape, filename));
                        }

                        parcelData.setLength(0);
                    }
                } else {
                    parcelData.append(line).append("\n");
                }
            }

            if (!parcelData.isEmpty()) {
                String shape = parcelData.toString().trim();

                if (isValidParcel(shape)) {
                    parcels.add(new Parcel(shape));
                    log.info("Посылка с формой {} добавлена в список", shape);
                } else {
                    throw new PackageShapeException(String.format("Неверная форма посылки: %s, из файла: %s", shape, filename));
                }
            }

        } catch (IOException ioException) {
            throw new FileNotFoundException(String.format("Файл %s не найден", filename));
        }

        return parcels;
    }
}