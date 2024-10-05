package ru.liga.consoleParcels.repository;

import ru.liga.consoleParcels.entity.Parcel;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с репозиторием посылок.
 */
public interface ParcelRepository {
    /**
     * Возвращает список всех посылок.
     *
     * @return Список всех посылок.
     */
    List<Parcel> findAll();

    /**
     * Находит посылку по имени.
     *
     * @param name Имя посылки.
     * @return Опциональная посылка, если она найдена, иначе пустой опционал.
     */
    Optional<Parcel> findParcelByName(String name);

    /**
     * Проверяет наличие посылки по имени.
     *
     * @param name Имя посылки.
     * @return {@code true}, если посылка существует, иначе {@code false}.
     */
    boolean existsByName(String name);

    /**
     * Сохраняет новую посылку.
     *
     * @param parcel Посылка для сохранения.
     */
    void save(Parcel parcel);

    /**
     * Удаляет посылку по имени.
     *
     * @param nameOfParcelForDelete Имя посылки для удаления.
     */
    void deleteParcelByParcelName(String nameOfParcelForDelete);
}
