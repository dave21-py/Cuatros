package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import app.model.Block;
import app.model.Square;

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

    @Test
    public void testGetAndSetSquares() {
        Square[] squares = new Square[3];
        Block block = new Block(squares,squares[0]);
        assertTrue(block.getSquares() instanceof Square[]);
        Square[] s = new Square[3];
        s[0] = new Square(3,3,'Y');
        s[1] = new Square(1,1,'B');
        s[2] = new Square(2,2,'G');
        block.setSquares(s);
        var sq = block.getSquares();
        assertTrue(sq[0].getX() == 3);
        assertTrue(sq[0].getY() == 3);
        assertTrue(sq[0].getColorCode() == 'Y');
        assertTrue(sq[1].getX() == 1);
        assertTrue(sq[1].getY() == 1);
        assertTrue(sq[1].getColorCode() == 'B');
        assertTrue(sq[2].getX() == 2);
        assertTrue(sq[2].getY() == 2);
        assertTrue(sq[2].getColorCode() == 'G');
    }

    @Test
    public void testGetAndSetPivot() {
        Square[] squares = new Square[3];
        Block block = new Block(squares, squares[0]);
        Square s = new Square(2,2,'B');
        block.setPivot(s);
        var p = block.getPivot();
        assertTrue(p.getX() == 2);
        assertTrue(p.getY() == 2);
        assertTrue(p.getColorCode() == 'B');
    }

    @Test
    public void testCreateBlock1() {
        var b = Block.createBlock1();
        assertTrue(b.getSquares()[0].getX() == 5); assertTrue(b.getSquares()[0].getY() == 0); assertTrue(b.getSquares()[0].getColorCode() == 'Y');
        assertTrue(b.getSquares()[1].getX() == 6); assertTrue(b.getSquares()[1].getY() == 0); assertTrue(b.getSquares()[1].getColorCode() == 'Y');
        assertTrue(b.getSquares()[2].getX() == 5); assertTrue(b.getSquares()[2].getY() == 1); assertTrue(b.getSquares()[2].getColorCode() == 'Y');
        assertTrue(b.getSquares()[3].getX() == 6); assertTrue(b.getSquares()[3].getY() == 1); assertTrue(b.getSquares()[3].getColorCode() == 'Y');
        var p = b.getPivot();
        assertTrue(p.getX() == 5); assertTrue(p.getY() == 0);assertTrue(p.getColorCode() == 'Y');
    }
    @Test
    public void testCreateBlock2() {
        var b = Block.createBlock2();
        assertTrue(b.getSquares()[0].getX() == 5); assertTrue(b.getSquares()[0].getY() == 0); assertTrue(b.getSquares()[0].getColorCode() == 'C');
        assertTrue(b.getSquares()[1].getX() == 6); assertTrue(b.getSquares()[1].getY() == 0); assertTrue(b.getSquares()[1].getColorCode() == 'C');
        assertTrue(b.getSquares()[2].getX() == 4); assertTrue(b.getSquares()[2].getY() == 0); assertTrue(b.getSquares()[2].getColorCode() == 'C');
        assertTrue(b.getSquares()[3].getX() == 7); assertTrue(b.getSquares()[3].getY() == 0); assertTrue(b.getSquares()[3].getColorCode() == 'C');
        var p = b.getPivot();
        assertTrue(p.getX() == 5); assertTrue(p.getY() == 0);assertTrue(p.getColorCode() == 'Y');
    }
    @Test
    public void testCreateBlock3() {
        var b = Block.createBlock3();
        assertTrue(b.getSquares()[0].getX() == 5); assertTrue(b.getSquares()[0].getY() == 0); assertTrue(b.getSquares()[0].getColorCode() == 'R');
        assertTrue(b.getSquares()[1].getX() == 4); assertTrue(b.getSquares()[1].getY() == 0); assertTrue(b.getSquares()[1].getColorCode() == 'R');
        assertTrue(b.getSquares()[2].getX() == 5); assertTrue(b.getSquares()[2].getY() == 1); assertTrue(b.getSquares()[2].getColorCode() == 'R');
        assertTrue(b.getSquares()[3].getX() == 6); assertTrue(b.getSquares()[3].getY() == 1); assertTrue(b.getSquares()[3].getColorCode() == 'R');
        var p = b.getPivot();
        assertTrue(p.getX() == 5); assertTrue(p.getY() == 1);assertTrue(p.getColorCode() == 'R');
    }
    @Test
    public void testCreateBlock4() {
        var b = Block.createBlock4();
        assertTrue(b.getSquares()[0].getX() == 4); assertTrue(b.getSquares()[0].getY() == 0); assertTrue(b.getSquares()[0].getColorCode() == 'B');
        assertTrue(b.getSquares()[1].getX() == 4); assertTrue(b.getSquares()[1].getY() == 1); assertTrue(b.getSquares()[1].getColorCode() == 'B');
        assertTrue(b.getSquares()[2].getX() == 5); assertTrue(b.getSquares()[2].getY() == 1); assertTrue(b.getSquares()[2].getColorCode() == 'B');
        assertTrue(b.getSquares()[3].getX() == 6); assertTrue(b.getSquares()[3].getY() == 1); assertTrue(b.getSquares()[3].getColorCode() == 'B');
        var p = b.getPivot();
        assertTrue(p.getX() == 5); assertTrue(p.getY() == 1);assertTrue(p.getColorCode() == 'B');
    }
    @Test
    public void testCreateBlock5() {
        var b = Block.createBlock5();
        assertTrue(b.getSquares()[0].getX() == 4); assertTrue(b.getSquares()[0].getY() == 1); assertTrue(b.getSquares()[0].getColorCode() == 'O');
        assertTrue(b.getSquares()[1].getX() == 5); assertTrue(b.getSquares()[1].getY() == 1); assertTrue(b.getSquares()[1].getColorCode() == 'O');
        assertTrue(b.getSquares()[2].getX() == 6); assertTrue(b.getSquares()[2].getY() == 1); assertTrue(b.getSquares()[2].getColorCode() == 'O');
        assertTrue(b.getSquares()[3].getX() == 6); assertTrue(b.getSquares()[3].getY() == 0); assertTrue(b.getSquares()[3].getColorCode() == 'O');
        var p = b.getPivot();
        assertTrue(p.getX() == 5); assertTrue(p.getY() == 1);assertTrue(p.getColorCode() == 'O');
    }
    @Test 
    public void testCreateBlock6() {
        var b = Block.createBlock6();
        assertTrue(b.getSquares()[0].getX() == 5); assertTrue(b.getSquares()[0].getY() == 0); assertTrue(b.getSquares()[0].getColorCode() == 'G');
        assertTrue(b.getSquares()[1].getX() == 4); assertTrue(b.getSquares()[1].getY() == 1); assertTrue(b.getSquares()[1].getColorCode() == 'G');
        assertTrue(b.getSquares()[2].getX() == 5); assertTrue(b.getSquares()[2].getY() == 1); assertTrue(b.getSquares()[2].getColorCode() == 'G');
        assertTrue(b.getSquares()[3].getX() == 6); assertTrue(b.getSquares()[3].getY() == 0); assertTrue(b.getSquares()[3].getColorCode() == 'G');
        var p = b.getPivot();
        assertTrue(p.getX() == 5); assertTrue(p.getY() == 1);assertTrue(p.getColorCode() == 'G');
    }
    @Test 
    public void testCreateBlock7() {
        var b = Block.createBlock7();
        assertTrue(b.getSquares()[0].getX() == 4); assertTrue(b.getSquares()[0].getY() == 1); assertTrue(b.getSquares()[0].getColorCode() == 'P');
        assertTrue(b.getSquares()[1].getX() == 5); assertTrue(b.getSquares()[1].getY() == 0); assertTrue(b.getSquares()[1].getColorCode() == 'P');
        assertTrue(b.getSquares()[2].getX() == 6); assertTrue(b.getSquares()[2].getY() == 1); assertTrue(b.getSquares()[2].getColorCode() == 'P');
        assertTrue(b.getSquares()[3].getX() == 5); assertTrue(b.getSquares()[3].getY() == 1); assertTrue(b.getSquares()[3].getColorCode() == 'P');
        var p = b.getPivot();
        assertTrue(p.getX() == 5); assertTrue(p.getY() == 1);assertTrue(p.getColorCode() == 'P');
    }
    @Test
    public void testRotateClockwise() {
        Block b = Block.createBlock7();
        b.rotateCClockwise();
        assertTrue(b.getSquares()[0].getX() == 5); assertTrue(b.getSquares()[0].getY() == 0); assertTrue(b.getSquares()[0].getColorCode() == 'P');
        assertTrue(b.getSquares()[1].getX() == 6); assertTrue(b.getSquares()[1].getY() == 1); assertTrue(b.getSquares()[1].getColorCode() == 'P');
        assertTrue(b.getSquares()[2].getX() == 5); assertTrue(b.getSquares()[2].getY() == 2); assertTrue(b.getSquares()[2].getColorCode() == 'P');
        assertTrue(b.getSquares()[3].getX() == 5); assertTrue(b.getSquares()[3].getY() == 1); assertTrue(b.getSquares()[3].getColorCode() == 'P');
    }
}
