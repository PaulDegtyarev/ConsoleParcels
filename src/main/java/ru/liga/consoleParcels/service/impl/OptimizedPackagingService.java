package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.consoleParcels.dto.ParcelCountDto;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.ParcelCountingService;
import ru.liga.consoleParcels.service.ParcelQuantityRecordingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ParcelCountingService parcelCountingService;
    private ParcelQuantityRecordingService parcelQuantityRecordingService;

    /**
     * Конструктор сервиса оптимизированной упаковки.
     *
     * @param truckFactory Фабрика для создания грузовиков.
     */
    @Autowired
    public OptimizedPackagingService(TruckFactory truckFactory, ParcelCountingService parcelCountingService, ParcelQuantityRecordingService parcelQuantityRecordingService) {
        this.truckFactory = truckFactory;
        this.parcelCountingService = parcelCountingService;
        this.parcelQuantityRecordingService = parcelQuantityRecordingService;
    }

    @Override
    public List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucksSize) {
        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = loadTrucks(parcels, trucksSize);

        List<TruckParcelCountDto> truckParcelCounts = countParcelsInTrucks(trucks);

        parcelQuantityRecordingService.writeParcelCountToJsonFile(truckParcelCounts);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcels, String trucksSize) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);

        parcels.stream()
                .peek(parcel -> log.trace("Обработка посылки с формой: {}", parcel.getShape()))
                .forEach(parcel -> trucks.stream()
                        .filter(truck -> truck.findPosition(parcel).isPresent())
                        .findFirst()
                        .ifPresentOrElse(truck -> {
                            Point position = truck.findPosition(parcel).orElseThrow();
                            truck.place(parcel, position.getX(), position.getY());
                            log.info("Посылка размещена в грузовике на позиции ({}, {})", position.getX(), position.getY());
                        }, () -> {
                            throw new PackingException("Не удалось разместить посылку: " + parcel);
                        }));

        return trucks;
    }

    private List<TruckParcelCountDto> countParcelsInTrucks(List<Truck> trucks) {
        List<TruckParcelCountDto> truckParcelCounts = new ArrayList<>();

        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            Map<String, Integer> parcelCountByShape = new HashMap<>();

            for (char[] row : truck.getSpace()) {
                for (char cell : row) {
                    if (cell != ' ') {
                        String shape = String.valueOf(cell);
                        parcelCountByShape.put(shape, parcelCountByShape.getOrDefault(shape, 0) + 1);
                    }
                }
            }

            List<ParcelCountDto> parcelCounts = parcelCountByShape.entrySet().stream()
                    .map(entry -> new ParcelCountDto(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            truckParcelCounts.add(new TruckParcelCountDto(i + 1, parcelCounts));
        }

        return truckParcelCounts;
    }
}