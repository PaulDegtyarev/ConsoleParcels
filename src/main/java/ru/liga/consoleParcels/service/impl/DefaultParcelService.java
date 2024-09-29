package ru.liga.consoleParcels.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelDto;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.ParcelService;

import java.util.List;

@Service
public class DefaultParcelService implements ParcelService {
    private ParcelRepository parcelRepository;

    @Autowired
    public DefaultParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public List<ParcelDto> findAllParcels() {
        return parcelRepository.findAll()
                .stream()
                .map(parcel -> new ParcelDto(
                        parcel.getName(),
                        parcel.getShape(),
                        parcel.getSymbol()
                ))
                .toList();
    }
}
