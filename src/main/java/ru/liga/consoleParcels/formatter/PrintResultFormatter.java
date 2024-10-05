package ru.liga.consoleParcels.formatter;

import ru.liga.consoleParcels.dto.UnPackedTruckDto;
import ru.liga.consoleParcels.model.Truck;

import java.util.List;

public interface PrintResultFormatter {
    StringBuilder transferPackagingResultsToConsole(List<Truck> trucks);

    StringBuilder transferUnpackingResultsToConsole(List<UnPackedTruckDto> unPackedTrucks);
}
