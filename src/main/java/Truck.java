import java.util.Arrays;

public class Truck {
    private static final int TRUCK_WIDTH = 6;
    private static final int TRUCK_HEIGHT = 6;
    private char[][] space;

    public Truck() {
        space = new char[TRUCK_HEIGHT][TRUCK_WIDTH];
        for (char[] row : space) {
            Arrays.fill(row, ' ');
        }
    }

    public boolean canFit(Package pkg, int x, int y) {
        if (x + pkg.getWidth() > TRUCK_WIDTH || y + pkg.getHeight() > TRUCK_HEIGHT) {
            return false;
        }

        for (int i = 0; i < pkg.getHeight(); i++) {
            for (int j = 0; j < pkg.getWidth(); j++) {
                if (space[y + i][x + j] != ' ') {
                    return false;
                }
            }
        }

        // Проверка наличия опоры снизу только для нижнего ряда пакета
        if (y + pkg.getHeight() < TRUCK_HEIGHT) {
            boolean hasSupport = false;
            for (int j = 0; j < pkg.getWidth(); j++) {
                if (space[y + pkg.getHeight()][x + j] != ' ') {
                    hasSupport = true;
                    break;
                }
            }
            if (!hasSupport) {
                return false; // пакет не может "левитировать"
            }
        }

        return true;
    }

    public void place(Package pkg, int x, int y) {
        if (x + pkg.getWidth() > TRUCK_WIDTH || y + pkg.getHeight() > TRUCK_HEIGHT) {
            throw new IllegalArgumentException("Package does not fit at the specified position");
        }
        for (int i = 0; i < pkg.getHeight(); i++) {
            for (int j = 0; j < pkg.getWidth(); j++) {
                space[y + i][x + j] = pkg.getId();
            }
        }
    }

    public Point findPosition(Package pkg) {
        for (int y = TRUCK_HEIGHT - pkg.getHeight(); y >= 0; y--) {
            for (int x = 0; x <= TRUCK_WIDTH - pkg.getWidth(); x++) {
                if (canFit(pkg, x, y)) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    public void print() {
        System.out.println("+      +");
        for (char[] row : space) {
            System.out.print("+");
            System.out.print(row);
            System.out.println("+");
        }
        System.out.println("++++++++");
        System.out.println();
    }

    public static int getTruckHeight() {
        return TRUCK_HEIGHT;
    }
}
