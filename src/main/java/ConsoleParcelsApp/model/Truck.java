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
    public boolean canFit(Parcel parcel, int x, int y) {
        if (x + parcel.getWidth() > TRUCK_WIDTH || y + parcel.getHeight() > TRUCK_HEIGHT) {
            return false;
        }
        for (int i = 0; i < parcel.getHeight(); i++) {
            for (int j = 0; j < parcel.getWidth(); j++) {
                if (space[y + i][x + j] != ' ') {
                    return false;
                }
            }
        }
        if (y + parcel.getHeight() < TRUCK_HEIGHT) {
            boolean hasSupport = false;
            for (int j = 0; j < parcel.getWidth(); j++) {
                if (space[y + parcel.getHeight()][x + j] != ' ') {
                    hasSupport = true;
                    break;
                }
            }
            if (!hasSupport) {
                return false;
            }
        }
        return true;
    }
    public void place(Parcel parcel, int x, int y) {
        char[][] shape = parcel.getShape();
        for (int i = 0; i < parcel.getHeight(); i++) {
            for (int j = 0; j < parcel.getWidth(); j++) {
                space[y + i][x + j] = parcel.getId();
                if (shape[i][j] != ' ') {
                    space[y + i][x + j] = shape[i][j];
                }
            }
        }
    }
    public Point findPosition(Parcel parcel) {
        for (int y = TRUCK_HEIGHT - parcel.getHeight(); y >= 0; y--) {
            for (int x = 0; x <= TRUCK_WIDTH - parcel.getWidth(); x++) {
                if (canFit(parcel, x, y)) {
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
