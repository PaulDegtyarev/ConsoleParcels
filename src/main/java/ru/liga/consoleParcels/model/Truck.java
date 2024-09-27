package ru.liga.consoleParcels.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * Представляет грузовик, в который можно загружать посылки.
 *
 * Грузовик имеет фиксированные ширину и высоту, а также
 * двумерный массив для хранения информации о расположении
 * посылок в его пространстве.
 */
public class Truck {
    private static final int TRUCK_WIDTH = 6;
    private static final int TRUCK_HEIGHT = 6;
    private final char[][] space;

    /**
     * Создает новый грузовик с пустым пространством.
     *
     * Пространство грузовика заполняется пробелами (' '),
     * которые обозначают пустые ячейки.
     */
    public Truck() {
        space = new char[TRUCK_HEIGHT][TRUCK_WIDTH];

        for (char[] row : space) {
            Arrays.fill(row, ' ');
        }
    }

    private boolean canFit(Parcel parcel, int x, int y) {
        if (x + parcel.getWidth() > TRUCK_WIDTH || y + parcel.getHeight() > TRUCK_HEIGHT) {
            return false;
        }
        for (int i = 0; i < parcel.getHeight(); i++) {
            for (int j = 0; j < parcel.getWidth(); j++) {
                if (parcel.getShape()[i][j] != ' ' && space[y + i][x + j] != ' ') {
                    return false;
                }
            }
        }

        if (y + parcel.getHeight() < TRUCK_HEIGHT) {

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

    /**
     * Размещает посылку в заданной позиции.
     *
     * Заполняет ячейки пространства грузовика символами
     * формы посылки.
     *
     * @param parcel Посылка, которую нужно разместить.
     * @param x Координата x начала размещения посылки.
     * @param y Координата y начала размещения посылки.
     */
    public void place(Parcel parcel, int x, int y) {
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
     * Ищет подходящую позицию для размещения посылки.
     *
     * Проверяет все возможные позиции в грузовике,
     * начиная с последней строки и двигаясь к первой.
     *
     * @param parcel Посылка, для которой нужно найти позицию.
     * @return Optional, содержащий координаты подходящей
     *         позиции, или пустой Optional, если позиция
     *         не найдена.
     */
    public Optional<Point> findPosition(Parcel parcel) {
        for (int y = TRUCK_HEIGHT - parcel.getHeight(); y >= 0; y--) {
            for (int x = 0; x <= TRUCK_WIDTH - parcel.getWidth(); x++) {
                if (canFit(parcel, x, y)) {
                    return Optional.of(new Point(x, y));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Возвращает количество занятых ячеек в пространстве грузовика.
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

    /**
     * Возвращает двумерный массив, представляющий пространство грузовика.
     *
     * @return Двумерный массив символов, представляющий пространство грузовика.
     */
    public char[][] getSpace() {
        return space;
    }
}
