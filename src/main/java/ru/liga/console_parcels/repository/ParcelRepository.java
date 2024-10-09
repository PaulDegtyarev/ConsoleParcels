package ru.liga.console_parcels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.console_parcels.entity.Parcel;

import java.util.Optional;

/**
 * Интерфейс для работы с репозиторием посылок.
 */
@Repository
public interface ParcelRepository extends JpaRepository<Parcel, String> {
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
}
