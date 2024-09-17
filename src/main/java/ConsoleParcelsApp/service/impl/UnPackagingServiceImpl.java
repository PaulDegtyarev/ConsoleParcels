package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.service.UnPackagingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
public class UnPackagingServiceImpl implements UnPackagingService {
    private static final String TRUCK_FILE_PATH = "data/trucks.json";

    @Override
    public void unpackTruck() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(TRUCK_FILE_PATH));

        JsonNode trucksNode = rootNode.get("trucks");

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

            printFormattedResults(truckId, finalCounts);
        }
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

    private void printFormattedResults(int truckId, Map<String, Integer> finalCounts) {
        StringBuilder builder = new StringBuilder();
        builder.append("Грузовик ").append(truckId).append(":\n");

        for (Map.Entry<String, Integer> entry : finalCounts.entrySet()) {
            String packageId = entry.getKey();
            int count = entry.getValue();

            if (count > 0) {
                builder.append(packageId).append(" - ").append(count).append(" шт.\n");
            } else {
                builder.append(packageId).append("\n");
            }
        }

        System.out.println(builder.toString());
    }
}
