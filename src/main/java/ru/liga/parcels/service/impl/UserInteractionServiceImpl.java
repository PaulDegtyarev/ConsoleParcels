package ru.liga.parcels.service.impl;

import ru.liga.parcels.service.UserInteractionService;
import lombok.extern.log4j.Log4j2;

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
    public String requestForFilePath() {
        String filePath;

        System.out.println("Введите путь к файлу: ");

        filePath = scanner.nextLine();

        return filePath;
    }

    @Override
    public int requestForAlgorithmChoice() {
        int algorithmChoice = 0;

        while (algorithmChoice == 0) {
            try {
                System.out.println("""
                        Выберите алгоритм:
                        1 - занимает максимальное пространство грузовика
                        2 - равномерная погрузка посылок по грузовикам
                        """);

                algorithmChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                log.error("Введен неверный номер алгоритма");
            }
        }

        return algorithmChoice;
    }
}
