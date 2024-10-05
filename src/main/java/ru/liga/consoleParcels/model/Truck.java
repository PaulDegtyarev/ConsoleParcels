package ru.liga.consoleParcels.model;

import lombok.Getter;
import ru.liga.consoleParcels.dto.ParcelForPackagingDto;

import java.util.Arrays;
import java.util.Optional;


public class Truck {
    @Getter
    private final int truckWidth;
    private final int truckHeight;
    @Getter
    private final char[][] space;


    public Truck(int truckWidth, int truckHeight) {
        this.truckWidth = truckWidth;
        this.truckHeight = truckHeight;
        space = new char[truckHeight][truckWidth];

        for (char[] row : space) {
            Arrays.fill(row, ' ');
        }
    }

    private boolean canFit(ParcelForPackagingDto parcel, int x, int y) {
        if (x + parcel.getWidth() > truckWidth || y + parcel.getHeight() > truckHeight) {
            return false;
        }
        for (int i = 0; i < parcel.getHeight(); i++) {
            for (int j = 0; j < parcel.getWidth(); j++) {
                if (parcel.getShape()[i][j] != ' ' && space[y + i][x + j] != ' ') {
                    return false;
                }
            }
        }

        if (y + parcel.getHeight() < truckHeight) {

            boolean hasSupport = false;

            for (int j = 0; j < parcel.getWidth(); j++) {
                if (parcel.getShape()[parcel.getHeight() - 1][j] != ' ' && space[y + parcel.getHeight()][x + j] != ' ') {
                    hasSupport = true;
                    break;
                }
            }

            return hasSupport;
        }

        return true;
    }

    public void place(ParcelForPackagingDto parcel, int x, int y) {
        char[][] shape = parcel.getShape();

        for (int i = 0; i < parcel.getHeight(); i++) {
            for (int j = 0; j < parcel.getWidth(); j++) {
                if (shape[i][j] != ' ') {
                    space[y + i][x + j] = shape[i][j];
                }
            }
        }
    }

    public Optional<Point> findPosition(ParcelForPackagingDto parcel) {
        for (int y = truckHeight - parcel.getHeight(); y >= 0; y--) {
            for (int x = 0; x <= truckWidth - parcel.getWidth(); x++) {
                if (canFit(parcel, x, y)) {
                    return Optional.of(new Point(x, y));
                }
            }
        }

        return Optional.empty();
    }

    public int getUsedSpace() {
        int usedSpace = 0;
        for (char[] row : space) {
            for (char cell : row) {
                if (cell != ' ') {
                    usedSpace++;
                }
            }
        }
        return usedSpace;
    }
}
