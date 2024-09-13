package ConsoleParcelsApp.util;

import ConsoleParcelsApp.model.Parcel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackageReader {
    private static final String[] ALLOWED_PARCELS = {
            "1",
            "22",
            "333",
            "4444",
            "55555",
            "666\n666",
            "777\n7777",
            "8888\n8888",
            "999\n999\n999"
    };

    private boolean isValidParcel(String input) {
        for (String allowedParcel : ALLOWED_PARCELS) {
            if (input.trim().equals(allowedParcel.trim())) {
                return true;
            }
        }

        return false;
    }

    public List<Parcel> readPackages(String filename) {
        List<Parcel> parcels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder parcelData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (!parcelData.isEmpty()) {
                        String shape = parcelData.toString().trim();

                        if (isValidParcel(shape)) {
                            parcels.add(new Parcel(shape));
                        }

                        else {
                            throw new IllegalArgumentException("Неверная форма посылки: " + shape);
                        }

                        parcelData.setLength(0);
                    }
                }

                else {
                    parcelData.append(line).append("\n");
                }
            }

            if (!parcelData.isEmpty()) {
                String shape = parcelData.toString().trim();

                if (isValidParcel(shape)) {
                    parcels.add(new Parcel(shape));
                }

                else {
                    throw new IllegalArgumentException("Неверная форма посылки: " + shape);
                }
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return parcels;
    }
}