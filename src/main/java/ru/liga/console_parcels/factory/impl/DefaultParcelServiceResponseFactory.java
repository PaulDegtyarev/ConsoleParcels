package ru.liga.console_parcels.factory.impl;

import org.springframework.stereotype.Component;
import ru.liga.console_parcels.dto.ParcelResponseDto;
import ru.liga.console_parcels.entity.Parcel;
import ru.liga.console_parcels.factory.ParcelServiceResponseFactory;

/**
 * Фабрика ответов сервиса по умолчанию.
 *
 * <p>
 * Этот класс реализует интерфейс {@link ParcelServiceResponseFactory} и предоставляет метод для создания ответа сервиса посылки
 * на основе объекта {@link Parcel}.
 * </p>
 */
@Component
public class DefaultParcelServiceResponseFactory implements ParcelServiceResponseFactory {
    /**
     * Метод для создания ответа сервиса посылки.
     *
     * @param parcel Объект посылки (DTO), на основе которого создается ответ.
     * @return Экземпляр {@link ParcelResponseDto}, представляющий ответ сервиса посылки.
     */
    @Override
    public ParcelResponseDto createServiceResponse(Parcel parcel) {
        return new ParcelResponseDto(
                parcel.getName(),
                parcel.getShape(),
                parcel.getSymbol()
        );
    }
}
