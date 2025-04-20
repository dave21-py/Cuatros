package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import app.model.*;

public class GameBoardTest {

    @Test
    public void testMoveBlockRight() {
        GameBoard board = new GameBoard();
        int originalX = board.getCurrentBlock().getSquares()[0].getX();

        board.moveBlockRight();

        int newX = board.getCurrentBlock().getSquares()[0].getX();
        assertEquals(originalX + 1, newX);
    }

    @Test
    public void testDropBlock() {
        GameBoard board = new GameBoard();
        int originalY = board.getCurrentBlock().getSquares()[0].getY();

        board.dropBlock();

        int newY = board.getCurrentBlock().getSquares()[0].getY();
        assertEquals(originalY + 1, newY);
    }

    @Test
    public void testBlockLocking() {
        GameBoard board = new GameBoard();

        // simulate dropping the block until it hits the bottom
        while (board.canMoveDown()) {
            board.dropBlock();
        }

        board.dropBlock(); // should lock now

        Square[][] grid = board.getGrid();
        boolean found = false;

        // ensure at least one square was added to the grid
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                if (grid[y][x] != null) {
                    found = true;
                    break;
                }
            }
        }

        assertTrue(found);
    }
}
