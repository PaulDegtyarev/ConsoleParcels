package ru.liga.parcels.service.impl;

import ru.liga.parcels.service.ReceivingUserRequestService;
import lombok.extern.log4j.Log4j2;
import ru.liga.parcels.util.UserChoice;

import java.util.Scanner;

@Log4j2
public class ReceivingUserRequestServiceImpl implements ReceivingUserRequestService {
    private Scanner scanner;

    public ReceivingUserRequestServiceImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public UserChoice requestUserChoice() {
        int userInput = 0;
        UserChoice userChoice = null;

        while (userInput == 0) {
            System.out.println("""
                1 - упаковать посылки в грузовик
                2 - распоковать грузовик и вывести количество посылок в нем
                3 - выйти из приложения""");

            try {
                userInput = Integer.parseInt(scanner.nextLine());
                userChoice = UserChoice.values()[userInput - 1];
            } catch (NumberFormatException numberFormatException) {
                log.error("Пользователь ввел не число: {}", userInput);
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                log.error("Некорректный параметр выбора пользователя. Ожидается значение от 1 до 3");
                userInput = 0;
            }
        }

        log.info("Пользователь выбрал опцию: {}", userChoice);

        return userChoice;
    }
}
