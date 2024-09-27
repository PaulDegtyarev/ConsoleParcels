package ru.liga.consoleParcels.builder;

import ru.liga.consoleParcels.builder.impl.PackagingCommandBuilderImpl;
import ru.liga.consoleParcels.dto.PackagingParametersDto;

/**
 * Интерфейс билдера для создания параметров упаковки.
 */
public interface PackagingCommandBuilder {

    /**
     * Устанавливает количество машин, на которые нужно загрузить посылки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    PackagingCommandBuilderImpl setNumberOfCars();

    /**
     * Устанавливает путь к файлу, откуда нужно брать данные для упаковки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    PackagingCommandBuilderImpl setInputFilePath();

    /**
     * Устанавливает выбор алгоритма упаковки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    PackagingCommandBuilderImpl setAlgorithmChoice();

    /**
     * Устанавливает путь к файлу, куда нужно записать результаты упаковки.
     *
     * @return Объект билдера для дальнейшей настройки.
     */
    PackagingCommandBuilderImpl setFilePathToWrite();

    /**
     * Создает объект {@link PackagingParametersDto} с заданными параметрами.
     *
     * @return Объект {@link PackagingParametersDto}.
     */
    PackagingParametersDto build();
}
