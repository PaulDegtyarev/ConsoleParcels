package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.exception.FileWriteException;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.TruckToJsonWriterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class TruckToJsonWriterServiceImpl implements TruckToJsonWriterService {
    private ObjectMapper objectMapper = new ObjectMapper();

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

    private void addTrucksToData (List<Truck> trucks, List<Map<String, Object>> truckList){
        int nextTruckId = truckList.size() + 1;
        for (Truck truck : trucks) {
            Map<String, Object> truckMap = new HashMap<>();
            truckMap.put("truckId", nextTruckId++);
            truckMap.put("packages", convertTruckSpaceToPackageList(truck));
            truckList.add(truckMap);
            log.trace("Добавлен грузовик с ID: {}", truckMap.get("truckId"));
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
