import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackageReader {
    public static List<Package> readPackages(String filename) throws IOException {
        List<Package> packages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder packageData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (packageData.length() > 0) {
                        packages.add(new Package(packageData.toString()));
                        packageData.setLength(0);
                    }
                } else {
                    packageData.append(line).append("\n");
                }
            }

            if (packageData.length() > 0) {
                packages.add(new Package(packageData.toString()));
            }
        }
        return packages;
    }
}
