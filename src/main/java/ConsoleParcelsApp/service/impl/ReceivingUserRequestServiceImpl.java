package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.service.ReceivingUserRequestService;
import lombok.extern.log4j.Log4j2;

import java.util.Scanner;

@Log4j2
public class ReceivingUserRequestServiceImpl implements ReceivingUserRequestService {
    private Scanner scanner;

    public ReceivingUserRequestServiceImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int requestUserChoice() {
        System.out.println("""
                1 - упаковать посылки в грузовик
                2 - распоковать грузовик и вывести количество посылок в нем""");

        int userChoice = 0;

        while (userChoice == 0) {
            try {
                userChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                log.error(numberFormatException.getMessage());
            }
        }

        log.info("Пользователь выбрал опцию: {}", userChoice);
        return userChoice;
    }
}
