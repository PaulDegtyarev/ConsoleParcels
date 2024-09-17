package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.TruckToJsonWriterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class TruckToJsonWriterServiceImpl implements TruckToJsonWriterService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void writeTruckToJson(List<Truck> trucks) {
        try {
            File file = new File("data/trucks.json");

            Map<String, Object> data;
            if (file.exists() && file.length() > 0) {
                data = objectMapper.readValue(file, Map.class);
            } else {
                data = new HashMap<>();
                data.put("trucks", new ArrayList<>());
            }

            List<Map<String, Object>> truckList = (List<Map<String, Object>>) data.get("trucks");

            for (Truck truck : trucks) {
                Map<String, Object> truckMap = new HashMap<>();
                truckMap.put("truckId", truckList.size() + 1);
                truckMap.put("packages", getPackages(truck));
                truckList.add(truckMap);
            }

            objectMapper.writeValue(file, data);

        } catch (IOException e) {
            log.error("Ошибка при записи в JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка при записи данных грузовиков в JSON", e);
        }
    }

    private List<List<Character>> getPackages(Truck truck) {
        List<List<Character>> packages = new ArrayList<>();
        char[][] space = truck.getSpace();
        for (char[] row : space) {
            List<Character> rowList = new ArrayList<>();
            for (char c : row) {
                rowList.add(c);
            }
            packages.add(rowList);
        }
        return packages;
    }
}
