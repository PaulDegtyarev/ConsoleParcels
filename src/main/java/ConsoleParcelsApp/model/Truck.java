package ConsoleParcelsApp.model;

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

    public boolean canFit(Parcel pkg, int x, int y) {
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

        if (y + pkg.getHeight() < TRUCK_HEIGHT) {
            int supportCount = 0;
            for (int j = 0; j < pkg.getWidth(); j++) {
                if (space[y + pkg.getHeight()][x + j] != ' ') {
                    supportCount++;
                }
            }
            if (supportCount <= pkg.getWidth() / 2) {
                return false;
            }
        }

        return true;
    }

    public void place(Parcel pkg, int x, int y) {
        if (x + pkg.getWidth() > TRUCK_WIDTH || y + pkg.getHeight() > TRUCK_HEIGHT) {
            throw new IllegalArgumentException("model.Package does not fit at the specified position");
        }
        char[][] shape = pkg.getShape();
        for (int i = 0; i < pkg.getHeight(); i++) {
            for (int j = 0; j < pkg.getWidth(); j++) {
                if (shape[i][j] != ' ') {
                    space[y + i][x + j] = shape[i][j]; // Копируем каждый символ формы пакета
                }
            }
        }
    }

    public Point findPosition(Parcel pkg) {
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
}
