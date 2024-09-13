package ConsoleParcelsApp.model;

import lombok.Getter;

@Getter
public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
