package ru.liga.consoleParcels.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.liga.consoleParcels.exception.FileNotFoundException;
import ru.liga.consoleParcels.mapper.ParcelData;
import ru.liga.consoleParcels.mapper.ParcelsInitData;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.repository.ParcelRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DefaultParcelRepository implements ParcelRepository {
    private final Map<String, Parcel> parcels = new HashMap<>();

    @Value("${parcels.init.data.file}")
    private String parcelsInitDataFile;

    @PostConstruct
    private void init() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ParcelsInitData initData = objectMapper.readValue(new File(parcelsInitDataFile), ParcelsInitData.class);

            for (ParcelData parcelData : initData.getParcels()) {
                Parcel parcel = new Parcel(parcelData.getShape(), parcelData.getSymbol().charAt(0), parcelData.getName());
                parcels.put(parcelData.getName(), parcel);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Ошибка чтения данных из файла: " + parcelsInitDataFile + " " + e.getMessage());
        }
    }

    @Override
    public List<Parcel> findAll() {
        return parcels.values()
                .stream()
                .toList();
    }
}
