package ru.liga.parcels.service.impl;

import ru.liga.parcels.exception.FileWriteException;
import ru.liga.parcels.model.Truck;
import ru.liga.parcels.service.TruckToJsonWriterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class TruckToJsonWriterServiceImpl implements TruckToJsonWriterService {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void writeTruckToJson(List<Truck> trucks, String filePath) {
        log.info("Начало процесса записи грузовиков в JSON файл");

        File file = new File(filePath);
        Map<String, Object> data = readOrCreateJsonData(file);

        List<Map<String, Object>> truckList = (List<Map<String, Object>>) data.get("trucks");
        addTrucksToData(trucks, truckList);

        try {
            objectMapper.writeValue(file, data);
            log.info("Успешно записано {} грузовиков в файл {}", trucks.size(), filePath);
        } catch (IOException e) {
            log.error("Ошибка при записи в JSON файл: {}", e.getMessage(), e);
            throw new FileWriteException("Ошибка при записи данных грузовиков в JSON");
        }
    }

    private Map<String, Object> readOrCreateJsonData(File file) {
        Map<String, Object> data = new HashMap<>();

        if (file.exists() && file.length() > 0) {
            try {
                data = objectMapper.readValue(file, Map.class);
                log.debug("Файл JSON существует и был прочитан");
            } catch (IOException e) {
                log.error("Ошибка при чтении существующего JSON файла, создается новый", e);
            }
        } else {
            data.put("trucks", new ArrayList<>());
            log.debug("Создан новый JSON объект для данных грузовиков");
        }

        return data;
    }

    private void addTrucksToData(List<Truck> trucks, List<Map<String, Object>> truckList) {
        for (Truck truck : trucks) {
            Map<String, Object> truckMap = new HashMap<>();
            truckMap.put("truckId", truckList.size() + 1);
            truckMap.put("packages", convertTruckSpaceToPackageList(truck));
            truckList.add(truckMap);
            log.trace("Добавлен грузовик с ID: {}", truckList.size());
        }
    }

    private List<List<Character>> convertTruckSpaceToPackageList(Truck truck) {
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
