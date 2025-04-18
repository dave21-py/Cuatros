package app.model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final int rows = 20;
    private final int cols = 10;
    private Square[][] grid;
    private Block currentBlock;

    private List<BoardObserver> observers = new ArrayList<>();

    public void addObserver(BoardObserver obs) {
        observers.add(obs);
    }

    // call notifyObservers when the board is changed
    private void notifyObservers() {
        for (BoardObserver o : observers)
            o.onBoardChanged();
    }

    // constructor
    public GameBoard() {
        grid = new Square[rows][cols];
        spawnNewBlock();
    }

    public void spawnNewBlock() {
        // first block "O" block at top center
        int center = cols / 2;
        Square[] squares = new Square[] {
                new Square(center, 0, 'Y'),
                new Square(center + 1, 0, 'Y'),
                new Square(center, 1, 'Y'),
                new Square(center + 1, 1, 'Y')
        };
        Square pivot = squares[0]; // top-left of O block (no rotation effect)
        currentBlock = new Block(squares, pivot);
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public Square[][] getGrid() {
        return grid;
    }

    // block actions
    public void dropBlock() {
        currentBlock.move(0, 1);
    }

    public void moveBlockLeft() {
        currentBlock.move(-1, 0);
    }

    public void moveBlockRight() {
        currentBlock.move(1, 0);
    }

    public void rotateBlock() {
        currentBlock.rotateClockwise();
    }

    // TODO: implement collision checks, line clearing, locking blocks
}
