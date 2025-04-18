package app.model;

public class Square {
    private int x;
    private int y;
    private char colorCode; // add color to block later

    // constructor
    public Square(int x, int y, char colorCode) {
        this.x = x;
        this.y = y;
        this.colorCode = colorCode;
    }
    
    // getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getColorCode() {
        return colorCode;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Square copy() {
        return new Square(x, y, colorCode);
    }
}
