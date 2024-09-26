package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.exception.FileReadException;
import ru.liga.consoleParcels.factory.DelimeterFactory;
import ru.liga.consoleParcels.service.UnPackagingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
public class UnPackagingServiceImpl implements UnPackagingService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private DelimeterFactory delimeterFactory;

    public UnPackagingServiceImpl(DelimeterFactory delimeterFactory) {
        this.delimeterFactory = delimeterFactory;
    }

    @Override
    public List<UnPackedTruckDto> unpackTruck(String filePath) {
        log.info("Начало процесса распаковки грузовиков из файла: {}", filePath);

        JsonNode rootNode = readJsonFile(filePath);
        JsonNode trucksNode = rootNode.get("trucks");

        List<UnPackedTruckDto> unPackedTrucks = new ArrayList<>();
        for (JsonNode truckNode : trucksNode) {
            int truckId = truckNode.get("truckId").asInt();
            Map<String, Integer> packageCounters = countPackages(truckNode.get("packages"));
            Map<String, Integer> finalCounts = calculatePackageCounts(packageCounters);
            unPackedTrucks.add(new UnPackedTruckDto(truckId, finalCounts));
            log.debug("Обработан грузовик с ID: {}", truckId);
        }

        log.info("Завершение процесса распаковки. Обработано {} грузовиков.", unPackedTrucks.size());
        return unPackedTrucks;
    }

    private JsonNode readJsonFile(String filePath) {
        try {
            return objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении JSON файла: {}\", filePath");
        }
    }

    private Map<String, Integer> countPackages(JsonNode packagesNode) {
        Map<String, Integer> packageCounters = new HashMap<>();

        for (JsonNode packageGroup : packagesNode) {
            if (packageGroup.isArray()) { // Проверяем, что это массив
                for (JsonNode packageElement : packageGroup) {
                    if (packageElement.isTextual()) { // Проверяем, что это текст
                        String packageId = packageElement.asText();
                        packageCounters.put(packageId, packageCounters.getOrDefault(packageId, 0) + 1);

                        System.out.print(packageId); // Выводим каждый элемент без пробела
                    }
                }
                System.out.println(); // Перевод строки после каждого внутреннего массива
            }
        }
        log.trace("Подсчитаны пакеты для одного грузовика");
        return packageCounters;
    }

    private Map<String, Integer> calculatePackageCounts(Map<String, Integer> packageCounters) {
        Map<String, Integer> finalCounts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : packageCounters.entrySet()) {
            String packageId = entry.getKey();
            int count = entry.getValue();

            switch (packageId) {
                case "1":
                    finalCounts.put(packageId, count);
                    break;
                case "2":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
                case "3":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
                case "4":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
                case "5":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
                case "6":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
                case "7":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
                case "8":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
                case "9":
                    finalCounts.put(packageId, count / delimeterFactory.createDelimeter(packageId));
                    break;
            }
        }

        log.trace("Рассчитаны финальные количества пакетов");
        return finalCounts;
    }
}