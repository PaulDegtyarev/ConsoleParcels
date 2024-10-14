package ru.liga.cargomanagement.factory.impl;

import org.springframework.stereotype.Component;
import ru.liga.cargomanagement.dto.ParcelResponseDto;
import ru.liga.cargomanagement.entity.Parcel;
import ru.liga.cargomanagement.factory.ParcelServiceResponseFactory;


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
