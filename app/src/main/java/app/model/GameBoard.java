package app.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class GameBoard {
    public DoubleProperty row = new SimpleDoubleProperty(-1);
    private final int rows = 20;
    private final int cols = 10;
    private Square[][] grid;
    private Block currentBlock;
    private Block nextBlock; // next block
    private Block holdBlock = null; // hold block
    private boolean holding = false; // if hold block pressed during current turn

    private List<BoardObserver> observers = new ArrayList<>();

    private boolean gameOver = false;

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

    public Block getNextBlock() {
        return nextBlock;
    }

    public Block getHoldBlock() {
        return holdBlock;
    }

    public Square[][] getGrid() {
        return grid;
    }

    public boolean checkGameOver() {
        return gameOver;
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
        if (canRotate(0, 0)) {
            currentBlock.rotateCClockwise();
        } else if (canRotate(-1, 0)) {
            currentBlock.move(-1, 0);
            currentBlock.rotateCClockwise();
        } else if (canRotate(1, 0)) {
            currentBlock.move(1, 0);
            currentBlock.rotateCClockwise();
        } else {
            // don't rotate if blocked
        }
    }

    // test rotation based on coordinates given
    private boolean canRotate(int dx, int dy) {
        for (Square square : currentBlock.getSquares()) {
            int relX = square.getX() - currentBlock.getPivot().getX();
            int relY = square.getY() - currentBlock.getPivot().getY();

            int newX = currentBlock.getPivot().getX() + relY + dx;
            int newY = currentBlock.getPivot().getY() - relX + dy;

            // check if new position would be valid
            if (newX < 0 || newX >= cols || newY < 0 || newY >= rows) {
                return false;
            }
            // check around existing blocks
            if (grid[newY][newX] != null) {
                return false;
            }
        }
        return true;
    }

    public void dropBlock() {
        if (canMoveDown()) {
            currentBlock.move(0, 1);
        } else {
            lockBlock();
            // check line cleared
            spawnNewBlock(); // move on to next block
        }

        notifyObservers(); // update the view
    }

    public void spawnNewBlock() {
        // check if next block collides
        for (Square square : nextBlock.getSquares()) {
            int x = square.getX();
            int y = square.getY();

            // end game when new block cannot be spawned
            if (x >= 0 && x < cols && y >= 0 && y < rows && grid[y][x] != null) {
                gameOver = true;
                return;
            }
        }
        currentBlock = new Block(nextBlock.getSquares(), nextBlock.getPivot());
        nextBlock = Block.generateBlock();
    }

    public void holdCurrentBlock() {
        if (holding) {
            return; // cannot hold again until you lock a block
        }

        if (holdBlock == null) {
            holdBlock = currentBlock;
            currentBlock = nextBlock;
            nextBlock = Block.generateBlock(); // prepare next block
        } else {
            Block temp = currentBlock;
            currentBlock = holdBlock;
            holdBlock = temp;
        }

        resetPosition();
        holding = true;
        notifyObservers();
    }

    public void resetPosition() {
        int newY = Integer.MAX_VALUE;
    
        // find the smallest Y coordinate in the block
        for (Square square : currentBlock.getSquares()) {
            newY = Math.min(newY, square.getY());
        }
    
        // calculate how far to shift the block upward
        int deltaY = -newY;
        currentBlock.move(0, deltaY);
    }

    // check how far the block needs to reset to spawn position
    public boolean canMoveUp() {
        for (Square square : currentBlock.getSquares()) {
            int newY = square.getY() - 1;

            // check top of board
            if (newY <= rows) {
                return false;
            }
        }
        return true;
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

    public boolean canMoveUp() {
        for (Square square : currentBlock.getSquares()) {
            int newY = square.getY() - 1;

            // check top of board
            if (newY <= rows) {
                return false;
            }
        }
        return true;
    }

    // continually move block down until locked in place
    public void hardDrop() {
        while (canMoveDown()) {
            currentBlock.move(0, 1);
        }
        lockBlock();
        spawnNewBlock();
        notifyObservers(); // redraw board immediately
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
        holding = false; // allows holding after second block is locked
    }

    public void removeRowAndAddNewRow(Square[][] original, int row, Square[] newRow) {
        Square[][] newGrid = new Square[grid.length][grid.length];
        int newRowIdx = 0;
        for (int i = 0; i < row; i++) {
            newGrid[newRowIdx++] = grid[i];
        }
        newGrid[newRowIdx++] = newRow;
        for (int i = row; i < grid.length - 1; i++) {
            newGrid[newRowIdx++] = grid[i + 1];
        }
        grid = newGrid;
    }

    public void removeGameBlocks() {
        Square[] square = new Square[10];
        for (int i = 0; i < grid.length; i++) {
            boolean instances = true;
            for (int j = 0; j < grid[i].length; j++) {
                if (!(grid[i][j] instanceof Square)) {
                    instances = false;

                } 
            }
            if (instances == true) {
                removeRowAndAddNewRow(grid, i, square);
                moveBlocksDown(grid, row.doubleValue());
                notifyObservers();
                row.set(i);
            } 
        } 
    }

    public void moveBlocksDown(Square[][] gridBlocks, double row) {
        for (Square[] array : gridBlocks) {
            for (Square s : array) {
                if (s!=null) {
                    s.setY(s.getY() + 1);
                }
            }
        }   
    }
}    


