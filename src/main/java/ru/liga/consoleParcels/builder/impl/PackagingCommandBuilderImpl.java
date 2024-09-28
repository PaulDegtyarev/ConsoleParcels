package ru.liga.consoleParcels.builder.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.consoleParcels.builder.PackagingCommandBuilder;
import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;

import java.util.Scanner;

/**
 * Реализация билдера для создания параметров упаковки.
 *
 * @see PackagingCommandBuilder
 */
@Log4j2
@Component
public class PackagingCommandBuilderImpl implements PackagingCommandBuilder {
    private int numberOfCars;
    private String inputFilePath;
    private UserAlgorithmChoice algorithmChoice;
    private String filePathToWrite;
    private final Scanner scanner;

    /**
     * Конструктор билдера.
     *
     * @param scanner Сканер для считывания ввода пользователя.
     */
    @Autowired
    public PackagingCommandBuilderImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Устанавливает количество машин, на которые нужно загрузить посылки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    @Override
    public PackagingCommandBuilderImpl setNumberOfCars() {
        int numberOfCars = 0;

        while (numberOfCars == 0) {
            try {
                System.out.println("Введите количество машин, на которые Вы хотите погрузить посылки: ");

                numberOfCars = Integer.parseInt(scanner.nextLine());

                this.numberOfCars = numberOfCars;
            } catch (NumberFormatException numberFormatException) {
                log.error("Введенно неверное количество машин");
            }
        }

        return this;
    }

    /**
     * Устанавливает путь к файлу, откуда нужно брать данные для упаковки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    @Override
    public PackagingCommandBuilderImpl setInputFilePath() {
        String inputFilePath;

        System.out.println("Введите путь к файлу откуда брать данные для упаковки: ");

        inputFilePath = scanner.nextLine();

        this.inputFilePath = inputFilePath;

        return this;
    }

    /**
     * Устанавливает выбор алгоритма упаковки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    @Override
    public PackagingCommandBuilderImpl setAlgorithmChoice() {
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

                this.algorithmChoice = userAlgorithmChoice;
            } catch (NumberFormatException | IndexOutOfBoundsException numberFormatException) {
                log.error("Введен неверный номер алгоритма");
                algorithmChoice = 0;
            }
        }

        return this;
    }

    /**
     * Устанавливает путь к файлу, куда нужно записать результаты упаковки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    @Override
    public PackagingCommandBuilderImpl setFilePathToWrite() {
        String filePathToWrite;

        System.out.println("Введите путь к файлу для записи загруженных грузовиков: ");

        filePathToWrite = scanner.nextLine();

        this.filePathToWrite = filePathToWrite;

        return this;
    }

    /**
     * Создает объект {@link PackagingParametersDto} с заданными параметрами.
     *
     * @return Объект {@link PackagingParametersDto}.
     */
    @Override
    public PackagingParametersDto build() {
        return new PackagingParametersDto(
                numberOfCars,
                inputFilePath,
                algorithmChoice,
                filePathToWrite
        );
    }
}
