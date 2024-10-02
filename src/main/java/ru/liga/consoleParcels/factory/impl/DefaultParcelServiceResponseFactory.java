package ru.liga.consoleParcels.factory.impl;

import org.springframework.stereotype.Component;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.factory.ParcelServiceResponseFactory;
import ru.liga.consoleParcels.model.Parcel;

@Component
public class DefaultParcelServiceResponseFactory implements ParcelServiceResponseFactory {
    @Override
    public ParcelResponseDto createServiceResponse(Parcel parcel) {
        return new ParcelResponseDto(
                parcel.getName(),
                parcel.getShape(),
                parcel.getSymbol()
        );
    }
}
