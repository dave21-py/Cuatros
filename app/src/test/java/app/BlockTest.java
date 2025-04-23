package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import app.model.*;

public class BlockTest {

    @Test
    public void testBlockMove() {
        Square[] squares = {
                new Square(1, 1, 'B'),
                new Square(2, 1, 'B'),
                new Square(1, 2, 'B'),
                new Square(2, 2, 'B')
        };
        Square pivot = new Square(1, 1, 'B');
        Block block = new Block(squares, pivot);

        block.move(1, 2);

        for (Square s : block.getSquares()) {
            assertTrue(s.getX() >= 2 && s.getY() >= 3); // every square shifted
        }
    }

    @Test
    public void testGenerateRandomBlock() {
        for (int i = 0; i < 20; i++) {
            Block b = Block.generateBlock();
            assertNotNull(b.getSquares());
            assertEquals(4, b.getSquares().length);
        }
    }

}
