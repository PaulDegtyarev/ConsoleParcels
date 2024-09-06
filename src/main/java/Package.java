public class Package {
    private int height;
    private int width;
    private char id;

    public Package(String input) {
        String[] lines = input.split("\n");
        this.height = lines.length;

        this.width = 0;
        for(String line : lines) {
            if(line.length() > this.width) {
                this.width = line.length();
            }
        }

        for(String line : lines) {
            if(!line.isEmpty()) {
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
}
