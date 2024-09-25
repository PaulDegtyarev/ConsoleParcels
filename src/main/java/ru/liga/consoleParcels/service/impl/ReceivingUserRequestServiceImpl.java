package ru.liga.consoleParcels.service.impl;

import ru.liga.consoleParcels.builder.PackagingCommandBuilder;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.service.ReceivingUserRequestService;
import lombok.extern.log4j.Log4j2;
import ru.liga.consoleParcels.model.UserCommand;

import java.util.Scanner;

@Log4j2
public class ReceivingUserRequestServiceImpl implements ReceivingUserRequestService {
    private Scanner scanner;
    private PackagingCommandBuilder packagingCommandBuilder;

    public ReceivingUserRequestServiceImpl(Scanner scanner, PackagingCommandBuilder packagingCommandBuilder) {
        this.scanner = scanner;
        this.packagingCommandBuilder = packagingCommandBuilder;
    }

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

    @Override
    public PackagingParametersDto requestParametersForPacking() {
        return packagingCommandBuilder
                .setNumberOfCars()
                .setInputFilePath()
                .setAlgorithmChoice()
                .setFilePathToWrite()
                .build();
    }

    @Override
    public String requestForFilePathToUnpackTruck() {
        System.out.println("Введите путь к файлу откуда брать данные для распаковки: ");

        return scanner.nextLine();
    }
}
