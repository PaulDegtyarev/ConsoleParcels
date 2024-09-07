public class Package {
    private int height;
    private int width;
    private char id;
    private char[][] shape;

    public Package(String input) {
        String[] lines = input.split("\n");
        this.height = lines.length;

        this.width = 0;
        for (String line : lines) {
            this.width = Math.max(this.width, line.length());
        }

        this.shape = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                shape[i][j] = lines[i].charAt(j);
            }

            // Заполнение пробелами оставшегося места, если строка короче максимальной ширины
            for (int j = lines[i].length(); j < width; j++) {
                shape[i][j] = ' ';
            }
        }

        // Устанавливаем id как первый символ первой непустой строки
        for (String line : lines) {
            if (!line.isEmpty()) {
                this.id = line.charAt(0);
                break;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char getId() {
        return id;
    }

    public char[][] getShape() {
        return shape;
    }

    public int getArea() {
        return width * height;
    }
}
