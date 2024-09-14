package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.service.UserInputService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class UserInputServiceImpl implements UserInputService {
    private static final Logger log = LogManager.getLogger(UserInputServiceImpl.class);
    private Scanner scanner;

    public UserInputServiceImpl(Scanner scanner) {
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
                        2 - один грузовик на одну посылку
                        3 - равномерная погрузка посылок по грузовикам
                        """);

                algorithmChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                log.error("Введен неверный номер алгоритма");
            }
        }

        return algorithmChoice;
    }
}
