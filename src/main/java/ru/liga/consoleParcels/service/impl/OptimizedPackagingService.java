package ru.liga.consoleParcels.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.liga.consoleParcels.dto.ParcelCountDto;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.PackagingService;
import lombok.extern.log4j.Log4j2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация сервиса оптимизированной упаковки.
 * <p>
 * Этот сервис сортирует посылки по площади в порядке убывания
 * и затем пытается разместить каждую посылку в первом
 * найденном свободном пространстве в грузовиках.
 *
 * @see PackagingService
 */
@Log4j2
public class OptimizedPackagingService implements PackagingService {
    private TruckFactory truckFactory;

    /**
     * Конструктор сервиса оптимизированной упаковки.
     *
     * @param truckFactory Фабрика для создания грузовиков.
     */
    public OptimizedPackagingService(TruckFactory truckFactory) {
        this.truckFactory = truckFactory;
    }

    @Override
    public List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucksSize) {
        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));
        log.trace("Посылки отсортированы по площади в порядке убывания");

        Map<String, Integer> parcelCountByShape = new HashMap<>();

        List<Truck> trucks = loadTrucks(parcels, trucksSize, parcelCountByShape);

        writeParcelCountToJsonFile(parcelCountByShape);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcels, String trucksSize, Map<String, Integer> parcelCountByShape) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);

        parcels.stream()
                .peek(parcel -> log.trace("Обработка посылки с формой: {}", parcel.getShape()))
                .forEach(parcel -> {
                    String shapeKey = getShapeKey(parcel.getShape());
                    parcelCountByShape.put(shapeKey, parcelCountByShape.getOrDefault(shapeKey, 0) + 1);

                    trucks.stream()
                            .filter(truck -> truck.findPosition(parcel).isPresent())
                            .findFirst()
                            .ifPresentOrElse(truck -> {
                                Point position = truck.findPosition(parcel).orElseThrow();
                                truck.place(parcel, position.getX(), position.getY());
                                log.info("Посылка размещена в грузовике на позиции ({}, {})", position.getX(), position.getY());
                            }, () -> {
                                throw new PackingException("Не удалось разместить посылку: " + parcel);
                            });
                });

        return trucks;
    }

    private String getShapeKey(char[][] shape) {
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < shape.length; i++) {
            keyBuilder.append(new String(shape[i]));
            if (i < shape.length - 1) {
                keyBuilder.append("\n");
            }
        }
        return keyBuilder.toString();
    }

    private void writeParcelCountToJsonFile(Map<String, Integer> parcelCountByShape) {
        List<ParcelCountDto> parcelCounts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : parcelCountByShape.entrySet()) {
            parcelCounts.add(new ParcelCountDto(entry.getKey(), entry.getValue()));
        }

        try (FileWriter writer = new FileWriter("data/trucks-with-number-of-parcels.json")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, parcelCounts);
        } catch (IOException e) {
            log.error("Ошибка при записи в JSON файл: ", e);
        }
    }
}