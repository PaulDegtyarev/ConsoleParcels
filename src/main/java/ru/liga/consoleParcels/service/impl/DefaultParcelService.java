package ru.liga.consoleParcels.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelDto;
import ru.liga.consoleParcels.exception.ParcelNotFoundException;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.ParcelService;

import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultParcelService implements ParcelService {
    private ParcelRepository parcelRepository;

    @Autowired
    public DefaultParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public String findAllParcels() {
        return parcelRepository.findAll()
                .stream()
                .map(parcel -> new ParcelDto(
                        parcel.getName(),
                        parcel.getShape(),
                        parcel.getSymbol()
                ))
                .map(ParcelDto::toString)
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public ParcelDto findParcelByName(String name) {
        log.info("Начинается поиск посылки с названием: {}", name.trim().toLowerCase());
        return parcelRepository.findParcelByName(name.trim().toLowerCase())
                .map(parcel -> new ParcelDto(
                            parcel.getName(),
                            parcel.getShape(),
                            parcel.getSymbol()
                ))
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием: " + name + " не найдена"));
    }
}
