package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.dto.TruckParcelCountDto;
import ru.liga.console_parcels.exception.PackingException;
import ru.liga.console_parcels.factory.TruckFactory;
import ru.liga.console_parcels.entity.ParcelPosition;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.service.TruckPackageService;
import ru.liga.console_parcels.service.ParcelCountService;
import ru.liga.console_parcels.service.RecordingService;

import java.util.List;

/**
 * Сервис для оптимизированной упаковки посылок в грузовики.
 */
@Log4j2
@Service
@Qualifier("MAX_SPACE")
@RequiredArgsConstructor
public class OptimizedPackagingService implements TruckPackageService {
    private final TruckFactory truckFactory;
    private final ParcelCountService parcelCountService;
    private final RecordingService recordingService;

    /**
     * Упаковывает посылки в грузовики.
     *
     * @param parcels    Список посылок для упаковки.
     * @param trucksSize Размер грузовиков.
     * @return Список грузовиков с упакованными посылками.
     */
    @Override
    public List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucksSize) {
        log.info("Начало процесса упаковки. Количество посылок: {}, размер грузовиков: {}", parcels.size(), trucksSize);
        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));

        List<Truck> trucks = loadTrucks(parcels, trucksSize);

        List<TruckParcelCountDto> truckParcelCounts = parcelCountService.count(trucks);

        recordingService.write(truckParcelCounts);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcels, String trucksSize) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);
        log.debug("Созданы грузовики: {}", trucks);

        parcels.forEach(parcel -> trucks.stream()
                        .filter(truck -> truck.findPosition(parcel).isPresent())
                        .findFirst()
                        .ifPresentOrElse(truck -> {
                            ParcelPosition position = truck.findPosition(parcel).orElseThrow();
                            truck.place(parcel, position.getX(), position.getY());
                            log.info("Посылка размещена в грузовике на позиции ({}, {})", position.getX(), position.getY());
                        }, () -> {
                            throw new PackingException("Не удалось разместить посылку: " + parcel);
                        }));

        return trucks;
    }
}