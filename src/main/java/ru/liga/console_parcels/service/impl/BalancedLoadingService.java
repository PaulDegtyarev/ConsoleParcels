package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.dto.TruckParcelCountDto;
import ru.liga.console_parcels.entity.ParcelPosition;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.entity.TruckPlacement;
import ru.liga.console_parcels.exception.PackingException;
import ru.liga.console_parcels.factory.TruckFactory;
import ru.liga.console_parcels.service.ParcelCountingService;
import ru.liga.console_parcels.service.ParcelQuantityRecordingService;
import ru.liga.console_parcels.service.TruckPackageService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для упаковки посылок со сбалансированной загрузкой грузовиков.
 */
@Log4j2
@Service
@Qualifier("EVEN_LOADING")
@RequiredArgsConstructor
public class BalancedLoadingService implements TruckPackageService {
    private final TruckFactory truckFactory;
    private final ParcelCountingService parcelCountingService;
    private final ParcelQuantityRecordingService parcelQuantityRecordingService;

    /**
     * Упаковывает посылки в грузовики.
     *
     * @param parcels    Список посылок для упаковки.
     * @param trucksSize Размер грузовиков.
     * @return Список грузовиков с упакованными посылками.
     */
    @Override
    public List<Truck> packPackages(List<ParcelForPackagingDto> parcels, String trucksSize) {
        log.info("Начало упаковки посылок. Количество посылок: {}, размер грузовиков: {}", parcels.size(), trucksSize);

        parcels.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));

        List<Truck> trucks = loadTrucks(parcels, trucksSize);
        log.info("Загрузка грузовиков завершена. Количество использованных грузовиков: {}", trucks.size());

        List<TruckParcelCountDto> truckParcelCounts = parcelCountingService.countParcelsInTrucks(trucks);

        parcelQuantityRecordingService.writeParcelCountToJsonFile(truckParcelCounts);

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcelForPackaging, String trucksSize) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);

        for (ParcelForPackagingDto parcel : parcelForPackaging) {
            placeParcelInBestPosition(trucks, parcel);
        }

        return trucks;
    }

    private void placeParcelInBestPosition(List<Truck> trucks, ParcelForPackagingDto parcelForPackaging) {
        int indexOffset = 1;

        trucks.stream()
                .map(truck -> {
                    Optional<ParcelPosition> position = truck.findPosition(parcelForPackaging);
                    return position.map(point -> new TruckPlacement(truck, point));
                })
                .flatMap(Optional::stream)
                .min(Comparator.comparingInt(tp -> tp.getTruck().getUsedSpace()))
                .ifPresentOrElse(bestPlacement -> {
                    Truck truck = bestPlacement.getTruck();
                    ParcelPosition position = bestPlacement.getPosition();
                    truck.place(parcelForPackaging, position.getX(), position.getY());
                    log.info("Посылка размещена в грузовике {} на позиции ({}, {})",
                            trucks.indexOf(truck) + indexOffset, position.getX(), position.getY());
                }, () -> {
                    throw new PackingException("Не удалось разместить посылку: " + parcelForPackaging);
                });
    }
}