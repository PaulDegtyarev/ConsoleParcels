package ru.liga.console_parcels.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.UnPackedTruckDto;
import ru.liga.console_parcels.exception.FileReadException;
import ru.liga.console_parcels.service.UnPackagingService;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Реализация сервиса для распаковки грузовиков.
 */
@Log4j2
@Service
public class DefaultUnPackagingService implements UnPackagingService {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Распаковывает грузовики из файлов и формирует данные о распаковке.
     *
     * @param truckFilePath       Путь к файлу с данными грузовиков.
     * @return Список DTO с данными о распаковке грузовиков.
     */
    @Override
    public List<UnPackedTruckDto> unpackTruck(String truckFilePath) {
        log.info("Начало процесса распаковки грузовиков из файлов: {}", truckFilePath);

        JsonNode trucksNode = readJsonFile(truckFilePath).get("trucks");

        List<UnPackedTruckDto> unPackedTrucks = new ArrayList<>();
        for (JsonNode truckNode : trucksNode) {
            int truckId = truckNode.get("truckId").asInt();
            JsonNode packagesNode = truckNode.get("packages");

            log.debug("Обработка грузовика с ID: {}", truckId);
            List<List<String>> packageLayout = createPackageLayout(packagesNode);
            log.trace("Схема расположения пакетов в грузовике:\n{}", packageLayout);
            Map<String, Integer> parcelCounts = calculateParcelCounts(packageLayout);
            log.trace("Количество посылок каждого типа:\n{}", parcelCounts);

            unPackedTrucks.add(new UnPackedTruckDto(truckId, parcelCounts, packageLayout));
            log.debug("Обработан грузовик с ID: {}", truckId);
        }

        log.info("Завершение процесса распаковки. Обработано {} грузовиков.", unPackedTrucks.size());
        return unPackedTrucks;
    }

    private JsonNode readJsonFile(String filePath) {
        log.debug("Чтение JSON файла: {}", filePath);
        try {
            return objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении JSON файла: " + filePath);
        }
    }

    private List<List<String>> createPackageLayout(JsonNode packagesNode) {
        log.debug("Создание схемы расположения пакетов.");

        List<List<String>> packageLayout = new ArrayList<>();
        for (JsonNode packageRow : packagesNode) {
            List<String> row = new ArrayList<>();
            for (JsonNode packageElement : packageRow) {
                row.add(packageElement.isTextual() ? packageElement.asText() : " ");
            }
            packageLayout.add(row);
        }

        log.trace("Схема расположения пакетов:\n{}", packageLayout);
        return packageLayout;
    }

    private Map<String, Integer> calculateParcelCounts(List<List<String>> packageLayout) {
        log.debug("Подсчет количества посылок каждого типа.");
        Map<String, Set<Character>> uniqueSymbols = new HashMap<>();
        Map<String, Integer> symbolCounts = new HashMap<>();

        for (List<String> row : packageLayout) {
            for (String cell : row) {
                if (!cell.trim().isEmpty()) {
                    symbolCounts.put(cell, symbolCounts.getOrDefault(cell, 0) + 1);
                    uniqueSymbols.computeIfAbsent(cell, k -> new HashSet<>()).add(cell.charAt(0));
                }
            }
        }

        Map<String, Integer> parcelCounts = new HashMap<>();
        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
            String form = entry.getKey();
            int count = entry.getValue();
            int delimeter = uniqueSymbols.get(form).size();
            int actualCount = count / delimeter;
            if (actualCount > 0) {
                parcelCounts.put(form, actualCount);
            }
        }

        log.trace("Количество посылок каждого типа:\n{}", parcelCounts);
        return parcelCounts;
    }
}