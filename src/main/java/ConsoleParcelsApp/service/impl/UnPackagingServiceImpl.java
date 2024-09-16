package ConsoleParcelsApp.service.impl;

import ConsoleParcelsApp.dto.TruckLoad;
import ConsoleParcelsApp.dto.TrucksContainer;
import ConsoleParcelsApp.model.Truck;
import ConsoleParcelsApp.service.UnPackagingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class UnPackagingServiceImpl implements UnPackagingService {
    private static final String TRUCK_FILE_PATH = "data/trucks.json";

    @Override
    public void unpackTruck() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(TRUCK_FILE_PATH));

        // Получаем список грузовиков
        JsonNode trucksNode = rootNode.get("trucks");

        // Итерируем по каждому грузовику
        for (JsonNode truckNode : trucksNode) {
            int truckId = truckNode.get("truckId").asInt();
            JsonNode packagesNode = truckNode.get("packages");

            // Карта для хранения счетчиков посылок для текущего грузовика
            Map<String, Integer> packageCounters = new HashMap<>();

            // Перебираем каждый набор посылок
            for (JsonNode packageGroup : packagesNode) {
                Iterator<JsonNode> packageIterator = packageGroup.elements();

                // Обрабатываем каждую посылку в наборе
                while (packageIterator.hasNext()) {
                    String packageId = packageIterator.next().asText();
                    packageCounters.put(packageId, packageCounters.getOrDefault(packageId, 0) + 1);
                }
            }

            // Применение правил для подсчета посылок:
            Map<String, Integer> finalCounts = calculatePackageCounts(packageCounters);

            // Выводим результат для текущего грузовика в нужном формате
            printFormattedResults(truckId, finalCounts);
        }
    }

    // Метод для применения правил к посылкам
    private Map<String, Integer> calculatePackageCounts(Map<String, Integer> packageCounters) {
        Map<String, Integer> finalCounts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : packageCounters.entrySet()) {
            String packageId = entry.getKey();
            int count = entry.getValue();

            System.out.println(packageId);
            switch (packageId) {
                case "1" -> finalCounts.put(packageId, count);
                case "22" -> finalCounts.put(packageId, count / 2);
                case "333" -> finalCounts.put(packageId, count / 3);
                case "4444" -> finalCounts.put(packageId, count / 4);
                case "55555" -> finalCounts.put(packageId, count / 5);
                case "666" -> finalCounts.put(packageId, count / 12);
                case "777" -> finalCounts.put(packageId, count / 6);
                case "8888" -> finalCounts.put(packageId, count / 8);
                case "999" -> finalCounts.put(packageId, count / 18);
                default -> finalCounts.put(packageId, count);
            }
        }

        return finalCounts;
    }

    // Метод для форматированного вывода результатов в консоль
    private void printFormattedResults(int truckId, Map<String, Integer> finalCounts) {
        StringBuilder builder = new StringBuilder();
        builder.append("Грузовик ").append(truckId).append(":\n");

        for (Map.Entry<String, Integer> entry : finalCounts.entrySet()) {
            String packageId = entry.getKey();
            int count = entry.getValue();

            // Форматируем вывод в зависимости от количества посылок
            if (count > 1) {
                builder.append(packageId).append(" - ").append(count).append(" шт.\n");
            } else {
                builder.append(packageId).append("\n");
            }
        }

        // Выводим результат в консоль
        System.out.println(builder.toString());
    }
}
