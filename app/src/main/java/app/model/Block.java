package app.model;

public class Block {
    private Square[] squares;
    private Square pivot; // rotation pivot point

    public Block(Square[] squares, Square pivot) {
        this.squares = squares;
        this.pivot = pivot;
    }

    public Square[] getSquares() {
        return squares;
    }

    // takes input of dx or dy and adds to current position
    public void move(int dx, int dy) {
        for (Square s : squares) {
            s.setX(s.getX() + dx);
            s.setY(s.getY() + dy);
        }
        pivot.setX(pivot.getX() + dx);
        pivot.setY(pivot.getY() + dy);
    }

    public void rotateClockwise() {
        for (Square s : squares) {
            int relX = s.getX() - pivot.getX();
            int relY = s.getY() - pivot.getY();
            // rotate 90 degrees clockwise
            // (x, y) becomes (y, -x)
            int newX = pivot.getX() + relY;
            int newY = pivot.getY() - relX;
            s.setX(newX);
            s.setY(newY);
        }
    }
}
