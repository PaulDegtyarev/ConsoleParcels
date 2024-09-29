package ru.liga.consoleParcels.repository;

import ru.liga.consoleParcels.model.Parcel;

import java.util.List;

public interface ParcelRepository {

    List<Parcel> findAll();
}
