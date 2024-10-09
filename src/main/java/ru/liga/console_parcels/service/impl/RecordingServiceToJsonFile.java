package ru.liga.console_parcels.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.exception.FileNotFoundException;
import ru.liga.console_parcels.exception.FileWriteException;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.service.FileWriterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация сервиса для записи данных о грузовиках в JSON файл.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class RecordingServiceToJsonFile implements FileWriterService {
    private final ObjectMapper objectMapper;

    /**
     * Записывает данные о грузовиках в JSON файл.
     *
     * @param trucks   Список грузовиков.
     * @param filePath Путь к файлу для записи.
     * @throws FileNotFoundException Если файл не существует.
     * @throws FileWriteException    Если произошла ошибка при записи файла.
     */
    @Override
    public void writeTruckToJson(List<Truck> trucks, String filePath) {
        log.info("Начало процесса записи грузовиков в JSON файл: {}", filePath);

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Файл JSON не существует: " + filePath);
        }

        try {
            List<Map<String, Object>> truckList = new ArrayList<>();

            addTrucksToData(trucks, truckList);

            Map<String, Object> data = new HashMap<>();
            data.put("trucks", truckList);

            objectMapper.writeValue(Files.newOutputStream(path), data);
            log.info("Успешно записано {} грузовиков в файл {}", trucks.size(), filePath);

        } catch (IOException e) {
            log.error("Ошибка при записи JSON файла: {}", e.getMessage(), e);
            throw new FileWriteException("Ошибка при работе с JSON файлом: " + filePath);
        }
    }

    private void addTrucksToData(List<Truck> trucks, List<Map<String, Object>> truckList) {
        log.debug("Начало добавления грузовиков в данные JSON");

        int nextTruckId = truckList.size() + 1;
        for (Truck truck : trucks) {
            Map<String, Object> truckMap = new HashMap<>();
            truckMap.put("truckId", nextTruckId++);
            truckMap.put("packages", convertTruckSpaceToPackageList(truck));
            truckList.add(truckMap);
            log.trace("Добавлен грузовик с ID: {}", truckMap.get("truckId"));
        }
        log.debug("Завершено добавление грузовиков в данные JSON");
    }

    private List<List<Character>> convertTruckSpaceToPackageList(Truck truck) {
        log.trace("Начало конвертации данных пространства грузовика в список пакетов.");

        List<List<Character>> packages = new ArrayList<>();
        char[][] space = truck.getSpace();
        for (char[] row : space) {
            List<Character> rowList = new ArrayList<>();
            for (char c : row) {
                rowList.add(c);
            }
            packages.add(rowList);
        }

        log.trace("Конвертированы данные пространства грузовика в список пакетов.");
        return packages;
    }
}
