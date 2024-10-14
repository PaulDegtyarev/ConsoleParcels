package ru.liga.cargomanagement.entity;

import lombok.Getter;
import ru.liga.cargomanagement.dto.ParcelForPackagingDto;

import java.util.Arrays;
import java.util.Optional;

/**
 * Класс сущности грузовика с возможностью упаковки посылок.
 */
@Getter
public class Truck {
    private final int width;
    private final int height;
    private final char[][] space;

    /**
     * Конструктор грузовика.
     *
     * @param width  Ширина грузовика.
     * @param height Высота грузовика.
     */
    public Truck(int width, int height) {
        this.width = width;
        this.height = height;
        space = new char[height][width];

        for (char[] row : space) {
            Arrays.fill(row, ' ');
        }
    }

    /**
     * Размещает посылку в указанной позиции.
     *
     * @param parcel DTO посылки для размещения.
     * @param x      Координата X начальной позиции.
     * @param y      Координата Y начальной позиции.
     */
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

    /**
     * Находит позицию для размещения посылки.
     *
     * @param parcel DTO посылки для размещения.
     * @return Optional (Point), если посылка может быть размещена, иначе пустой Optional.
     */
    public Optional<ParcelPosition> findPosition(ParcelForPackagingDto parcel) {
        for (int y = height - parcel.getHeight(); y >= 0; y--) {
            for (int x = 0; x <= width - parcel.getWidth(); x++) {
                if (canFit(parcel, x, y)) {
                    return Optional.of(new ParcelPosition(x, y));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Возвращает количество занятых ячеек пространства грузовика.
     *
     * @return Количество занятых ячеек.
     */
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

    private boolean canFit(ParcelForPackagingDto parcel, int x, int y) {
        if (x + parcel.getWidth() > width || y + parcel.getHeight() > height) {
            return false;
        }
        for (int i = 0; i < parcel.getHeight(); i++) {
            for (int j = 0; j < parcel.getWidth(); j++) {
                if (parcel.getShape()[i][j] != ' ' && space[y + i][x + j] != ' ') {
                    return false;
                }
            }
        }

        if (y + parcel.getHeight() < height) {

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
}
