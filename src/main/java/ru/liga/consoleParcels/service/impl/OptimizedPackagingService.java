package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.dto.TruckParcelCountDto;
import ru.liga.consoleParcels.exception.PackingException;
import ru.liga.consoleParcels.factory.TruckFactory;
import ru.liga.consoleParcels.model.Point;
import ru.liga.consoleParcels.model.Truck;
import ru.liga.consoleParcels.service.PackagingService;
import ru.liga.consoleParcels.service.ParcelCountingService;
import ru.liga.consoleParcels.service.ParcelQuantityRecordingService;

import java.util.List;

/**
 * Сервис для оптимизированной упаковки посылок в грузовики.
 */
@Log4j2
@Service
public class OptimizedPackagingService implements PackagingService {
    private TruckFactory truckFactory;
    private ParcelCountingService parcelCountingService;
    private ParcelQuantityRecordingService parcelQuantityRecordingService;

    /**
     * Конструктор сервиса с зависимостями.
     *
     * @param truckFactory                   Фабрика грузовиков.
     * @param parcelCountingService          Сервис подсчета посылок.
     * @param parcelQuantityRecordingService Сервис записи количества посылок.
     */
    @Autowired
    public OptimizedPackagingService(TruckFactory truckFactory, ParcelCountingService parcelCountingService, ParcelQuantityRecordingService parcelQuantityRecordingService) {
        this.truckFactory = truckFactory;
        this.parcelCountingService = parcelCountingService;
        this.parcelQuantityRecordingService = parcelQuantityRecordingService;
    }

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
        log.trace("Посылки отсортированы по площади в порядке убывания");

        List<Truck> trucks = loadTrucks(parcels, trucksSize);
        log.debug("Создано {} грузовиков для упаковки.", trucks.size());

        List<TruckParcelCountDto> truckParcelCounts = parcelCountingService.countParcelsInTrucks(trucks);
        log.debug("Подсчитано количество посылок в каждом грузовике.");

        parcelQuantityRecordingService.writeParcelCountToJsonFile(truckParcelCounts);
        log.info("Информация о количестве посылок в каждом грузовике успешно записана в JSON файл.");

        log.info("Упаковка завершена успешно. Все посылки размещены.");
        return trucks;
    }

    private List<Truck> loadTrucks(List<ParcelForPackagingDto> parcels, String trucksSize) {
        List<Truck> trucks = truckFactory.createTrucks(trucksSize);
        log.debug("Созданы грузовики: {}", trucks);

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
}