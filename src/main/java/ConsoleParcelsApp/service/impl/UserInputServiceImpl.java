package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.exception.WrongInputForNumberCarException;
import ConsoleParcelsApp.service.UserInputService;

import java.util.Scanner;

public class UserInputServiceImpl implements UserInputService {
    private Scanner scanner;

    public UserInputServiceImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int requestForNumberOfCars() {
        int numberOfCars;

        try {
            System.out.println("Введите количество машин, на которые Вы хотите погрузить посылки: ");

            numberOfCars = Integer.parseInt(scanner.nextLine());

            return numberOfCars;
        } catch (NumberFormatException numberFormatException) {
            throw new WrongInputForNumberCarException(numberFormatException.getMessage());
        }
    }
}
