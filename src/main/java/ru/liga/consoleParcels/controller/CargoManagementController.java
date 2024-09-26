package ru.liga.consoleParcels.controller;

import com.fasterxml.jackson.databind.JsonNode;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.model.Truck;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.service.*;
import ru.liga.consoleParcels.service.impl.PackageReader;
import ru.liga.consoleParcels.model.UserCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class CargoManagementController {
    private ReceivingUserRequestService receivingUserRequestService;
    private PackagingSelectionService packagingSelectionService;
    private PackageReader packageReader;
    private TruckToJsonWriterService truckToJsonWriterService;
    private UnPackagingService unPackagingService;

    public CargoManagementController(ReceivingUserRequestService receivingUserRequestService, PackagingSelectionService packagingSelectionService, PackageReader packageReader, TruckToJsonWriterService truckToJsonWriterService, UnPackagingService unPackagingService) {
        this.receivingUserRequestService = receivingUserRequestService;
        this.packagingSelectionService = packagingSelectionService;
        this.packageReader = packageReader;
        this.truckToJsonWriterService = truckToJsonWriterService;
        this.unPackagingService = unPackagingService;
    }

    public void handlePackagingOrUnpackingSelection() {
        log.info("Начата обработка выбора упаковки или распаковки");
        boolean isRunning = true;

        while (isRunning) {
            UserCommand userCommand = receivingUserRequestService.requestUserChoice();
            log.debug("Получен выбор пользователя: {}", userCommand);

            switch (userCommand) {
                case PACK:
                    log.info("Пользователь выбрал упаковку");
                    log.info("Начало процесса упаковки");

                    PackagingParametersDto packagingParameters = receivingUserRequestService.requestParametersForPacking();
                    log.debug("Количество машин для упаковки: {}", packagingParameters.getNumberOfCars());
                    log.info("Начало упаковки: файл = {}, количество автомобилей = {}", packagingParameters.getInputFilePath(), packagingParameters.getNumberOfCars());
                    log.debug("Выбранный алгоритм упаковки: {}", packagingParameters.getAlgorithmChoice());

                    PackagingService packagingService = packagingSelectionService.selectPackagingService(packagingParameters.getAlgorithmChoice());
                    log.debug("Выбран сервис для упаковки: {}", packagingService);

                    List<Truck> trucks;
                    try {
                        List<Parcel> parcels = packageReader.readPackages(packagingParameters.getInputFilePath());
                        log.debug("Прочитано {} посылок из файла {}", parcels.size(), packagingParameters.getInputFilePath());

                        log.info("Начинается упаковка {} машин из файла {}", packagingParameters.getNumberOfCars(), packagingParameters.getInputFilePath());
                        trucks = packagingService.packPackages(parcels, packagingParameters.getNumberOfCars());
                        log.info("Упаковка завершена. Упаковано {} грузовиков", trucks.size());

                        truckToJsonWriterService.writeTruckToJson(trucks, packagingParameters.getFilePathToWrite());
                        log.info("Запись результатов упаковки в JSON завершена");
                    } catch (PackingException | FileWriteException | PackageShapeException |
                             FileNotFoundException packingException) {
                        log.error(packingException.getMessage());
                        break;
                    }

                    if (trucks.isEmpty()) {
                        log.debug("Посылки не были упакованы");
                    } else {
                        log.info("Начало печати результатов упаковки для {} грузовиков", trucks.size());

                        for (int i = 0; i < trucks.size(); i++) {
                            log.debug("Печать информации для грузовика {}", i + 1);
                            System.out.println("Truck " + (i + 1) + ":");

                            System.out.println(trucks.get(i).toConsoleFormat());
                        }

                        log.info("Завершение печати результатов упаковки");
                    }
                    break;
                case UNPACK:
                    log.info("Пользователь выбрал распаковку");
                    log.info("Начало процесса распаковки");

                    String filePathToUnpack = receivingUserRequestService.requestForFilePathToUnpackTruck();
                    log.debug("Путь к файлу с данными для распаковки: {}", filePathToUnpack);

                    List<UnPackedTruckDto> unpackedTrucks = new ArrayList<>();
                    try {
                        unpackedTrucks = unPackagingService.unpackTruck(filePathToUnpack);
                        log.info("Распаковка завершена. Распаковано {} машин", unpackedTrucks.size());
                    } catch (FileReadException fileReadException) {
                        log.error("Ошибка при чтении файла {}: {}", filePathToUnpack, fileReadException.getMessage());
                    }

                    if (unpackedTrucks.isEmpty()) {
                        log.debug("Посылки не были распакованы");
                    } else {
                        log.info("Начало печати результатов распаковки для {} грузовиков", unpackedTrucks.size());

                        StringBuilder builder = new StringBuilder();

                        for (UnPackedTruckDto unPackedTruck : unpackedTrucks) {
                            int truckId = unPackedTruck.getTruckId();
                            Map<String, Integer> finalCounts = unPackedTruck.getPackageCounts();
                            List<List<String>> packageLayout = unPackedTruck.getPackageLayout();  // Получаем расположение посылок

                            log.debug("Генерация строки для грузовика ID: {}", truckId);

                            // Добавляем верхнюю границу и информацию о грузовике

                            builder.append("Грузовик ").append(truckId).append(":");
                            builder.append("\n");

                            // Выводим схему расположения посылок в грузовике
                            for (List<String> row : packageLayout) {
                                builder.append("+");  // Левая граница строки
                                for (String packageId : row) {
                                    builder.append(packageId);  // Выводим каждую ячейку с пакетом
                                }
                                builder.append("+\n");  // Правая граница строки
                            }

                            // Выводим итоговое количество посылок
                            builder.append("++++++++\n");
                            builder.append("Количество посылок: \n");
                            for (Map.Entry<String, Integer> entry : finalCounts.entrySet()) {
                                String packageId = entry.getKey();
                                int count = entry.getValue();

                                if (count > 0) {
                                    builder.append(packageId).append(" - ").append(count).append(" шт.\n");
                                }
                            }

                            builder.append("\n");
                        }

                        // Выводим на экран результат
                        System.out.println(builder);
                        log.info("Завершение печати результатов распаковки");
                    }
                    break;
                case EXIT:
                    log.info("Пользователь выбрал выход из приложения");
                    isRunning = false;
                    break;
                default:
                    log.warn("Некорректный параметр выбора пользователя: {}. Ожидается значение 1 - 3.", userCommand);
                    break;
            }
        }
    }
}