package ru.liga.parcels.service.impl;

import ru.liga.parcels.service.UserInteractionService;
import lombok.extern.log4j.Log4j2;
import ru.liga.parcels.util.UserAlgorithmChoice;

import java.util.Scanner;

@Log4j2
public class UserInteractionServiceImpl implements UserInteractionService {
    private Scanner scanner;

    public UserInteractionServiceImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int requestForNumberOfCars() {
        int numberOfCars = 0;

        while (numberOfCars == 0) {
            try {
                System.out.println("Введите количество машин, на которые Вы хотите погрузить посылки: ");

                numberOfCars = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                log.error("Введенно неверное количество машин");
            }
        }

        return numberOfCars;
    }

    @Override
    public String requestForInputFilePath() {
        String inputFilePath;

        System.out.println("Введите путь к файлу откуда брать данные для упаковки: ");

        inputFilePath = scanner.nextLine();

        return inputFilePath;
    }

    @Override
    public UserAlgorithmChoice requestForAlgorithmChoice() {
        int algorithmChoice = 0;
        UserAlgorithmChoice userAlgorithmChoice = null;

        while (algorithmChoice == 0) {
            System.out.println("""
                        Выберите алгоритм:
                        1 - занимает максимальное пространство грузовика
                        2 - равномерная погрузка посылок по грузовикам""");
            try {
                algorithmChoice = Integer.parseInt(scanner.nextLine());
                userAlgorithmChoice = UserAlgorithmChoice.values()[algorithmChoice - 1];
            } catch (NumberFormatException numberFormatException) {
                log.error("Введен неверный номер алгоритма");
                algorithmChoice = 0;
            }
        }

        return userAlgorithmChoice;
    }

    @Override
    public String requestForFilePathToWrite() {
        String filePathToWrite;

        System.out.println("Введите путь к файлу для записи загруженных грузовиков: ");

        filePathToWrite = scanner.nextLine();

        return filePathToWrite;
    }
}
