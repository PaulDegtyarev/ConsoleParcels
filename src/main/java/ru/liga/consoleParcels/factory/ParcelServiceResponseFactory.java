package ru.liga.consoleParcels.factory;

import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.model.Parcel;

public interface ParcelServiceResponseFactory {
    ParcelResponseDto createServiceResponse(Parcel parcel);
}
