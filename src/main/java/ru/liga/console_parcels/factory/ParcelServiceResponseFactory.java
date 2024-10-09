package ru.liga.console_parcels.factory;

import ru.liga.console_parcels.dto.ParcelResponseDto;
import ru.liga.console_parcels.entity.Parcel;

/**
 * Интерфейс фабрики ответов сервиса посылки.
 *
 * <p>
 * Интерфейс определяет метод для создания ответа сервиса посылки на основе объекта {@link Parcel}.
 * </p>
 */
public interface ParcelServiceResponseFactory {
    /**
     * Метод для создания ответа сервиса посылки.
     *
     * @param parcel Объект посылки (DTO), на основе которого создается ответ.
     * @return Экземпляр {@link ParcelResponseDto}, представляющий ответ сервиса посылки.
     */
    ParcelResponseDto createServiceResponse(Parcel parcel);
}
