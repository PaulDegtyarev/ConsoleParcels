package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.dto.UnPackedTruckDto;
import ConsoleParcelsApp.service.UnPackagingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
public class UnPackagingServiceImpl implements UnPackagingService {
    @Override
    public List<UnPackedTruckDto> unpackTruck(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));

        JsonNode trucksNode = rootNode.get("trucks");

        List<UnPackedTruckDto> unPackedTrucks = new ArrayList<>();

        for (JsonNode truckNode : trucksNode) {
            int truckId = truckNode.get("truckId").asInt();

            JsonNode packagesNode = truckNode.get("packages");

            Map<String, Integer> packageCounters = new HashMap<>();

            for (JsonNode packageGroup : packagesNode) {
                Iterator<JsonNode> packageIterator = packageGroup.elements();

                while (packageIterator.hasNext()) {
                    String packageId = packageIterator.next().asText();
                    packageCounters.put(packageId, packageCounters.getOrDefault(packageId, 0) + 1);
                }
            }

            Map<String, Integer> finalCounts = calculatePackageCounts(packageCounters);

            unPackedTrucks.add(new UnPackedTruckDto(truckId, finalCounts));
        }

        return unPackedTrucks;
    }

    private Map<String, Integer> calculatePackageCounts(Map<String, Integer> packageCounters) {
        Map<String, Integer> finalCounts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : packageCounters.entrySet()) {
            String packageId = entry.getKey();
            int count = entry.getValue();

            switch (packageId) {
                case "1":
                    finalCounts.put(packageId, count);
                    break;
                case "2":
                    finalCounts.put(packageId, count / 2);
                    break;
                case "3":
                    finalCounts.put(packageId, count / 3);
                    break;
                case "4":
                    finalCounts.put(packageId, count / 4);
                    break;
                case "5":
                    finalCounts.put(packageId, count / 5);
                    break;
                case "6":
                    finalCounts.put(packageId, count / 6);
                    break;
                case "7":
                    finalCounts.put(packageId, count / 7);
                    break;
                case "8":
                    finalCounts.put(packageId, count / 8);
                    break;
                case "9":
                    finalCounts.put(packageId, count / 9);
                    break;
            }
        }

        return finalCounts;
    }
}
