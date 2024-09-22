package ru.liga.parcels.model;

import lombok.Getter;

@Getter
public class Parcel {
    private final int height;
    private final int width;
    private final char id;
    private char[][] shape;

    public Parcel(String input) {
        String[] lines = input.split("\n");

        this.height = lines.length;

        int maxWidth = 0;
        char parcelId = ' ';

        for (String line : lines) {
            if (!line.isEmpty() && parcelId == ' ') {
                parcelId = line.charAt(0);
            }
            maxWidth = Math.max(maxWidth, line.length());
        }

        this.width = maxWidth;
        this.id = parcelId;

        this.shape = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                shape[i][j] = lines[i].charAt(j);
            }

            for (int j = lines[i].length(); j < width; j++) {
                shape[i][j] = ' ';
            }
        }
    }

    public int getArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "id=" + id +
                '}';
    }
}