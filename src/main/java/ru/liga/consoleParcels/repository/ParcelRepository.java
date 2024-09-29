package ru.liga.consoleParcels.repository;

import ru.liga.consoleParcels.model.Parcel;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository {

    List<Parcel> findAll();

    Optional<Parcel> findParcelByName(String name);
}
