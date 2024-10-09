package ru.liga.console_parcels.factory.impl;

import org.springframework.stereotype.Component;
import ru.liga.console_parcels.dto.ParcelResponseDto;
import ru.liga.console_parcels.entity.Parcel;
import ru.liga.console_parcels.factory.ParcelServiceResponseFactory;


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
