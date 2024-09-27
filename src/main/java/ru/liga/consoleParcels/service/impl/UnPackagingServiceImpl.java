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

/**
 * Реализация сервиса для распаковки грузовиков из JSON
 * файла.
 * <p>
 * Этот сервис считывает данные о грузовиках из JSON
 * файла, обрабатывает их и возвращает список объектов
 * {@link UnPackedTruckDto}, представляющих информацию о
 * каждом грузовике.
 *
 * @see UnPackagingService
 */
@Log4j2
public class UnPackagingServiceImpl implements UnPackagingService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private DelimeterFactory delimeterFactory;

    /**
     * Конструктор сервиса распаковки грузовиков.
     *
     * @param delimeterFactory Фабрика для создания
     *                         разделителей.
     */
    public UnPackagingServiceImpl(DelimeterFactory delimeterFactory) {
        this.delimeterFactory = delimeterFactory;
    }

    /**
     * Распаковывает данные о грузовиках из JSON файла.
     *
     * @param filePath Путь к файлу JSON, содержащему данные
     *                 о грузовиках.
     * @return Список объектов {@link UnPackedTruckDto},
     * представляющих информацию о каждом
     * распакованном грузовике.
     * @throws FileReadException Если произошла ошибка при
     *                           чтении файла.
     */
    @Override
    public List<UnPackedTruckDto> unpackTruck(String filePath) {
        log.info("Начало процесса распаковки грузовиков из файла: {}", filePath);

        JsonNode rootNode = readJsonFile(filePath);
        JsonNode trucksNode = rootNode.get("trucks");

        List<UnPackedTruckDto> unPackedTrucks = new ArrayList<>();
        for (JsonNode truckNode : trucksNode) {
            int truckId = truckNode.get("truckId").asInt();
            JsonNode packagesNode = truckNode.get("packages");

            List<List<String>> packageLayout = new ArrayList<>();
            for (JsonNode packageRow : packagesNode) {
                List<String> row = new ArrayList<>();
                for (JsonNode packageElement : packageRow) {
                    row.add(packageElement.isTextual() ? packageElement.asText() : " ");
                }
                packageLayout.add(row);
            }

            Map<String, Integer> packageCounters = countPackages(packagesNode);
            Map<String, Integer> finalCounts = calculatePackageCounts(packageCounters);

            unPackedTrucks.add(new UnPackedTruckDto(truckId, finalCounts, packageLayout));
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
            for (JsonNode packageElement : packageGroup) {
                String packageId = packageElement.asText();
                packageCounters.put(packageId, packageCounters.getOrDefault(packageId, 0) + 1);
                log.debug("Посылка {} положена", packageId);
            }
        }
        log.trace("Подсчитаны посылки для одного грузовика");
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