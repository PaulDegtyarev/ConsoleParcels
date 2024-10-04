package ru.liga.consoleParcels.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;
import ru.liga.consoleParcels.service.ParcelCountingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultParcelCountingService implements ParcelCountingService {
    @Override
    public Map<String, Integer> countParcelByShape(List<ParcelForPackagingDto> parcels) {
        Map<String, Integer> parcelCountByShape = new HashMap<>();

        parcels.forEach(parcel -> {
            String shapeKey = getParcelShape(parcel.getShape());
            parcelCountByShape.put(shapeKey, parcelCountByShape.getOrDefault(shapeKey, 0) + 1);
        });

        return parcelCountByShape;
    }

    private String getParcelShape(char[][] shape) {
        StringBuilder shapeOfParcel = new StringBuilder();

        for (int i = 0; i < shape.length; i++) {
            shapeOfParcel.append(new String(shape[i]));
            if (i < shape.length - 1) {
                shapeOfParcel.append("\n");
            }
        }

        return shapeOfParcel.toString();
    }
}
