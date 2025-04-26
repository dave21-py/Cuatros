package app.model;

import java.util.Random;

public class Block {
    private Square[] squares;
    private Square pivot; // rotation pivot point

    private static final Random RANDOM = new Random();

    public Block() {

    }

    public Block(Square[] squares, Square pivot) {
        this.squares = squares;
        this.pivot = pivot;
    }

    public Square[] getSquares() {
        return squares;
    }

    public Square getPivot() {
        return pivot;
    }

    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

    public void setPivot(Square pivot) {
        this.pivot = pivot;
    }

    public static Block generateBlock() {
        int nextCode = RANDOM.nextInt(7) + 1;

        return switch (nextCode) {
            case 1 -> createBlock1();
            case 2 -> createBlock2();
            case 3 -> createBlock3();
            case 4 -> createBlock4();
            case 5 -> createBlock5();
            case 6 -> createBlock6();
            case 7 -> createBlock7();
            default -> throw new IllegalStateException("Invalid block code");
        };
    }

    // block 1: o shape (square)
    public static Block createBlock1() {
        Square[] shape = new Square[] {
                new Square(5, 0, 'Y'),
                new Square(6, 0, 'Y'),
                new Square(5, 1, 'Y'),
                new Square(6, 1, 'Y')
        };
        Square pivot = new Square(5, 0, 'Y');
        return new Block(shape, pivot);
    }

    // block 2: 4-long I shape
    public static Block createBlock2() {
        Square[] shape = new Square[] {
                new Square(5, 0, 'C'),
                new Square(6, 0, 'C'),
                new Square(4, 0, 'C'),
                new Square(7, 0, 'C')
        };
        Square pivot = new Square(5, 0, 'Y');
        return new Block(shape, pivot);
    }

    // block 3: Z shape
    public static Block createBlock3() {
        Square[] shape = new Square[] {
                new Square(5, 0, 'R'),
                new Square(4, 0, 'R'),
                new Square(5, 1, 'R'),
                new Square(6, 1, 'R')
        };
        Square pivot = new Square(5, 1, 'R');
        return new Block(shape, pivot);
    }

    // block 4: J shape
    public static Block createBlock4() {
        Square[] shape = new Square[] {
                new Square(4, 0, 'B'),
                new Square(4, 1, 'B'),
                new Square(5, 1, 'B'),
                new Square(6, 1, 'B')
        };
        Square pivot = new Square(5, 1, 'B');
        return new Block(shape, pivot);
    }

    // block 5: L shape
    public static Block createBlock5() {
        Square[] shape = new Square[] {
                new Square(4, 1, 'O'),
                new Square(5, 1, 'O'),
                new Square(6, 1, 'O'),
                new Square(6, 0, 'O')
        };
        Square pivot = new Square(5, 1, 'O');
        return new Block(shape, pivot);
    }

    // block 6: S shape
    public static Block createBlock6() {
        Square[] shape = new Square[] {
                new Square(5, 0, 'G'),
                new Square(4, 1, 'G'),
                new Square(5, 1, 'G'),
                new Square(6, 0, 'G')
        };
        Square pivot = new Square(5, 1, 'G');
        return new Block(shape, pivot);
    }

    // block 7: T shape
    public static Block createBlock7() {
        Square[] shape = new Square[] {
                new Square(4, 1, 'P'),
                new Square(5, 0, 'P'),
                new Square(6, 1, 'P'),
                new Square(5, 1, 'P')
        };
        Square pivot = new Square(5, 1, 'P');
        return new Block(shape, pivot);
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
