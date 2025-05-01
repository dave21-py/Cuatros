package app.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameBoard {
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    private final int rows = 20;
    private final int cols = 10;
    private Square[][] grid;
    private Block currentBlock;
    private Block nextBlock; // next block
    private Block holdBlock = null; // hold block
    private boolean holding = false; // if hold block pressed during current turn

    private List<BoardObserver> observers = new ArrayList<>();

    private boolean gameOver = false;

    public static StringProperty scoreNumber = new SimpleStringProperty();

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
        addScore(50); // +50 per placed block

        holding = false; // allows holding after second block is locked
    }

    public void removeGameBlocks() {
        int rowsCleared = 0; // tracks row cleared this turn

        for (int y = rows - 1; y >= 0; y--) {
            boolean fullRow = true;
            for (int x = 0; x < cols; x++) {
                if (grid[y][x] == null) {
                    fullRow = false;
                    break;
                }
            }

            if (fullRow) {
                removeRow(y);
                moveRowsDown(y);
                y++; 
                rowsCleared++;
            }
        }

        if (rowsCleared > 0) {
            addScore(checkRowsCleared(rowsCleared));
        }
        notifyObservers();
    }

    private int checkRowsCleared(int rows) {
        return switch (rows) {
            case 1 -> 200;
            case 2 -> 300;
            case 3 -> 500;
            case 4 -> 1000;
            default -> 100;
        };
    }

    private void removeRow(int y) {
        for (int x = 0; x < cols; x++) {
            grid[y][x] = null;
        }
    }    

    private void moveRowsDown(int fromRow) {
        for (int y = fromRow - 1; y >= 0; y--) {
            for (int x = 0; x < cols; x++) {
                if (grid[y][x] != null) {
                    // move the square down
                    grid[y + 1][x] = grid[y][x];
                    grid[y + 1][x].setY(y + 1);
                    grid[y][x] = null; // clear the old spot
                }
            }
        }
    }

    public IntegerProperty scoreProperty() {
        return score;
    }
    
    public int getScore() {
        return score.get();
    }
    
    public void addScore(int points) {
        score.set(score.get() + points);
    }
    
}
