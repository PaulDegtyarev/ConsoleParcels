package ru.liga.cargomanagement.factory;

import ru.liga.cargomanagement.dto.ParcelResponseDto;
import ru.liga.cargomanagement.entity.Parcel;

/**
 * Интерфейс фабрики, создающей ответы сервиса посылок.
 */
public interface ParcelServiceResponseFactory {
    /**
     * Метод для создания ответа сервиса посылки.
     *
     * @param parcel DTO посылки, на основе которого создается ответ.
     * @return Экземпляр {@link ParcelResponseDto}, представляющий ответ сервиса посылки.
     */
    ParcelResponseDto createServiceResponse(Parcel parcel);
}
