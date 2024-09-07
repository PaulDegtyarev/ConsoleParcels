import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackagePacker {
    private List<Truck> trucks;

    public PackagePacker() {
        trucks = new ArrayList<>();
        trucks.add(new Truck());
    }

    public void packPackages(List<Package> packages) {
        packages.sort((p1, p2) -> Integer.compare(p2.getArea(), p1.getArea()));

        for (Package pkg : packages) {
            System.out.println("Trying to place package " + pkg.getId() + " with height " + pkg.getHeight() + " and width " + pkg.getWidth());

            boolean placed = false;

            for (Truck truck : trucks) {
                Point position = truck.findPosition(pkg);
                if (position != null) {
                    truck.place(pkg, position.getX(), position.getY());
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                Truck newTruck = new Truck();
                Point position = newTruck.findPosition(pkg);
                if (position != null) {
                    newTruck.place(pkg, position.getX(), position.getY());
                    trucks.add(newTruck);
                }
            }
        }
    }

    public void printResults() {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");
            trucks.get(i).print();
        }
    }

    public static void main(String[] args) {
        String filePath = "/home/paul/IdeaProjects/ConsoleParcels/input-data.txt";

        try {
            List<Package> packages = PackageReader.readPackages(filePath);
            PackagePacker packer = new PackagePacker();
            packer.packPackages(packages);
            packer.printResults();
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
    }
}