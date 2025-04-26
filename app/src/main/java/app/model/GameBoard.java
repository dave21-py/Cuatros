package app.model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final int rows = 20;
    private final int cols = 10;
    private Square[][] grid;
    private Block currentBlock;
    private Block nextBlock; //next block

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
        nextBlock = Block.generateBlock();
        spawnNewBlock();
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public Block getNextBlock(){
        return nextBlock;
    }

    public Square[][] getGrid() {
        return grid;
    }

    // block actions
    public void moveBlockLeft() {
        for (Square square : currentBlock.getSquares()) {
            int newX = square.getX() - 1;
            
            // check if beyond right edge
            if (newX < 0 || grid[square.getY()][newX] != null) {
                return;
            }
        }
    
        currentBlock.move(-1, 0);
    }

    public void moveBlockRight() {
        for (Square square : currentBlock.getSquares()) {
            int newX = square.getX() + 1;
    
            // check if beyond right edge
            if (newX >= cols) {
                return; 
            }
    
            // check if the next cell already filled
            if (grid[square.getY()][newX] != null) {
                return;
            }
        }
        currentBlock.move(1, 0);
    }
    

    public void rotateBlock() {
        currentBlock.rotateClockwise();
    }

    public void dropBlock() {
        if (canMoveDown()) {
            currentBlock.move(0, 1);
        } else {
            lockBlock();
            // check line cleared
            spawnNewBlock();   // move on to next block
        }
    
        notifyObservers();     // update the view
    }
    

    public void spawnNewBlock() {
        currentBlock = new Block(nextBlock.getSquares(), nextBlock.getPivot());
        nextBlock = Block.generateBlock();

        // Block nextBlock = Block.generateBlock(); 
        // currentBlock = new Block(nextBlock.getSquares(), nextBlock.getPivot());
    } 

    // check if block should stop and spawn new block
    public boolean canMoveDown() {
        for (Square square : currentBlock.getSquares()) {
            int newY = square.getY() + 1;
    
            // check bottom of board
            if (newY >= rows) {
                return false;
            }
    
            // check collision with locked grid squares
            if (grid[newY][square.getX()] != null) {
                return false;
            }
        }
        return true;
    }

    // keep block in place once in position
    public void lockBlock() {
        for (Square square : currentBlock.getSquares()) {
            int x = square.getX();
            int y = square.getY();
    
            if (y >= 0 && y < rows && x >= 0 && x < cols) {
                grid[y][x] = new Square(x, y, square.getColorCode());
            }

        }
    }
}    

