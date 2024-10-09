package ru.liga.console_parcels.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.UnpackedTruckDto;
import ru.liga.console_parcels.exception.FileReadException;
import ru.liga.console_parcels.service.FileUnpackingService;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Реализация сервиса для распаковки грузовиков в формате .json.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class JsonFileUnpackingService implements FileUnpackingService {
    @Autowired
    private final ObjectMapper objectMapper;

    @Override
    public List<UnpackedTruckDto> unpackTruck(String truckFilePath) {
        log.info("Начало процесса распаковки грузовиков из файлов: {}", truckFilePath);

        JsonNode trucksNode = readJsonFile(truckFilePath).get("trucks");

        List<UnpackedTruckDto> unPackedTrucks = new ArrayList<>();
        for (JsonNode truckNode : trucksNode) {
            int truckId = truckNode.get("truckId").asInt();
            JsonNode packagesNode = truckNode.get("packages");

            List<List<String>> packageLayout = createPackageLayout(packagesNode);
            Map<String, Integer> parcelCounts = calculateParcelCounts(packageLayout);

            unPackedTrucks.add(new UnpackedTruckDto(truckId, parcelCounts, packageLayout));
            log.info("Обработан грузовик с ID: {}", truckId);
        }

        log.info("Завершение процесса распаковки. Обработано {} грузовиков.", unPackedTrucks.size());
        return unPackedTrucks;
    }

    private JsonNode readJsonFile(String filePath) {
        try {
            return objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении JSON файла: " + filePath);
        }
    }

    private List<List<String>> createPackageLayout(JsonNode packagesNode) {
        List<List<String>> packageLayout = new ArrayList<>();
        for (JsonNode packageRow : packagesNode) {
            List<String> row = new ArrayList<>();
            for (JsonNode packageElement : packageRow) {
                row.add(packageElement.isTextual() ? packageElement.asText() : " ");
            }
            packageLayout.add(row);
        }

        return packageLayout;
    }

    private Map<String, Integer> calculateParcelCounts(List<List<String>> packageLayout) {
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

        return parcelCounts;
    }
}