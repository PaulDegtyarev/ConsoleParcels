package ru.liga.consoleParcels.builder;

import ru.liga.consoleParcels.builder.impl.PackagingCommandBuilderImpl;
import ru.liga.consoleParcels.dto.PackagingParametersDto;

public interface PackagingCommandBuilder {
    PackagingCommandBuilderImpl setNumberOfCars();

    PackagingCommandBuilderImpl setInputFilePath();

    PackagingCommandBuilderImpl setAlgorithmChoice();

    PackagingCommandBuilderImpl setFilePathToWrite();

    PackagingParametersDto build();
}
