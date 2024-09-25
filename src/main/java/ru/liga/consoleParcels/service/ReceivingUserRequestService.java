package ru.liga.consoleParcels.service;

import ru.liga.consoleParcels.dto.PackagingParametersDto;
import ru.liga.consoleParcels.model.UserCommand;

public interface ReceivingUserRequestService {
    UserCommand requestUserChoice();

    PackagingParametersDto requestParametersForPacking();

    String requestForFilePathToUnpackTruck();
}
