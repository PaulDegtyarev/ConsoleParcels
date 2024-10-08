package ru.liga.consoleParcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.ParcelPosition;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.model.TruckPlacement;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.ParcelCountingService;
import ru.liga.consoleParcels.service.ParcelQuantityRecordingService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для упаковки посылок со сбалансированной загрузкой грузовиков.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class BalancedLoadingService implements PackagingService {
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
        log.debug("Посылки отсортированы по площади в порядке убывания");

        log.trace("Отсортированные посылки: {}", parcels);

        List<Truck> trucks = loadTrucks(parcels, trucksSize);
        log.info("Загрузка грузовиков завершена. Количество использованных грузовиков: {}", trucks.size());

        List<TruckParcelCountDto> truckParcelCounts = parcelCountingService.countParcelsInTrucks(trucks);
        log.debug("Подсчет посылок в грузовиках завершен");

        parcelQuantityRecordingService.writeParcelCountToJsonFile(truckParcelCounts);
        log.info("Запись количества посылок в JSON-файл завершена");

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcels, String trucksSize) {
        int indexOffset = 1;
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);
        log.debug("Создано {} грузовиков", trucks.size());

        for (int i = 0; i < parcels.size(); i++) {
            ParcelForPackagingDto parcel = parcels.get(i);
            log.debug("Обработка посылки {}/{}: {}", i + indexOffset, parcels.size(), parcel);

            trucks.stream()
                    .map(truck -> {
                        Optional<ParcelPosition> position = truck.findPosition(parcel);
                        return position.map(point -> new TruckPlacement(truck, point));
                    })
                    .flatMap(Optional::stream)
                    .min(Comparator.comparingInt(tp -> tp.getTruck().getUsedSpace()))
                    .ifPresentOrElse(placement -> {
                        Truck truck = placement.getTruck();
                        ParcelPosition position = placement.getPosition();
                        truck.place(parcel, position.getX(), position.getY());

                        log.info("Посылка размещена в грузовике {} на позиции ({}, {})",
                                trucks.indexOf(truck) + indexOffset, position.getX(), position.getY());
                    }, () -> {
                        throw new PackingException("Не удалось разместить посылку: " + parcel);
                    });
        }

        log.debug("Загрузка грузовиков завершена. Состояние грузовиков: {}",
                trucks.stream().map(truck -> "Грузовик " + (trucks.indexOf(truck) + indexOffset) + ": " + truck.getUsedSpace() + " занято")
                        .collect(Collectors.joining(", ")));

        return trucks;
    }
}