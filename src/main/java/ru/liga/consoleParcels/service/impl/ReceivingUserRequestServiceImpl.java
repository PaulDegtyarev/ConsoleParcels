package ru.liga.consoleParcels.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.builder.PackagingCommandBuilder;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.service.ReceivingUserRequestService;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.model.UserCommand;

import java.util.Scanner;

/**
 * Реализация сервиса для получения запросов от пользователя.
 * <p>
 * Этот сервис использует {@link Scanner} для считывания
 * ввода пользователя и {@link PackagingCommandBuilder} для
 * создания объекта {@link PackagingParametersDto} с
 * параметрами упаковки.
 *
 * @see ReceivingUserRequestService
 */
@Log4j2
@Service
public class ReceivingUserRequestServiceImpl implements ReceivingUserRequestService {
    private Scanner scanner;
    private PackagingCommandBuilder packagingCommandBuilder;

    /**
     * Конструктор сервиса получения запросов от пользователя.
     *
     * @param scanner                 Сканер для считывания
     *                                ввода пользователя.
     * @param packagingCommandBuilder Билдер для создания
     *                                объекта
     *                                {@link PackagingParametersDto}.
     */
    @Autowired
    public ReceivingUserRequestServiceImpl(Scanner scanner, PackagingCommandBuilder packagingCommandBuilder) {
        this.scanner = scanner;
        this.packagingCommandBuilder = packagingCommandBuilder;
    }

    /**
     * Запрашивает у пользователя выбор действия.
     *
     * @return Выбранное пользователем действие.
     */
    @Override
    public UserCommand requestUserChoice() {
        int userInput = 0;
        UserCommand userCommand = null;

        while (userInput == 0) {
            System.out.println("""
                    1 - упаковать посылки в грузовик
                    2 - распоковать грузовик и вывести количество посылок в нем
                    3 - выйти из приложения""");

            try {
                userInput = Integer.parseInt(scanner.nextLine());
                userCommand = UserCommand.values()[userInput - 1];
            } catch (NumberFormatException numberFormatException) {
                log.error("Пользователь ввел не число: {}", userInput);
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                log.error("Некорректный параметр выбора пользователя. Ожидается значение от 1 до 3");
                userInput = 0;
            }
        }

        log.info("Пользователь выбрал опцию: {}", userCommand);
        return userCommand;
    }

    /**
     * Запрашивает у пользователя параметры для упаковки.
     *
     * @return Объект {@link PackagingParametersDto},
     * содержащий параметры для упаковки.
     */
    @Override
    public PackagingParametersDto requestParametersForPacking() {
        return packagingCommandBuilder
                .setNumberOfCars()
                .setInputFilePath()
                .setAlgorithmChoice()
                .setFilePathToWrite()
                .build();
    }

    /**
     * Запрашивает у пользователя путь к файлу для распаковки
     * грузовика.
     *
     * @return Путь к файлу для распаковки грузовика.
     */
    @Override
    public String requestForFilePathToUnpackTruck() {
        System.out.println("Введите путь к файлу откуда брать данные для распаковки: ");

        return scanner.nextLine();
    }
}
