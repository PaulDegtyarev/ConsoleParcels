package ru.liga.consoleParcels.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
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
import java.util.Optional;

@Log4j2
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
                Parcel parcel = new Parcel(parcelData.getName().trim().toLowerCase(), parcelData.createShape(), parcelData.getSymbol().charAt(0));
                parcels.put(parcelData.getName().toLowerCase(), parcel);
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

    @Override
    public Optional<Parcel> findParcelByName(String name) {
        return Optional.ofNullable(parcels.get(name));
    }

    @Override
    public boolean existsByName(String name) {
        return parcels.containsKey(name);
    }

    @Override
    public void save(Parcel parcel) {
        log.info("Добавляется посылка с названием = {}", parcel.getName());
        parcels.put(parcel.getName(), parcel);
        log.info("Добавлена посылка с id = {}", parcels.size() + 1);
    }
}
