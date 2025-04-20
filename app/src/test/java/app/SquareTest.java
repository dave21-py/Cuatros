package app;

import org.junit.jupiter.api.Test;

import app.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {

    @Test
    public void testSquare() {
        Square s = new Square(3, 5, 'R');

        assertEquals(3, s.getX());
        assertEquals(5, s.getY());
        assertEquals('R', s.getColorCode());

        s.setX(4);
        s.setY(6);

        assertEquals(4, s.getX());
        assertEquals(6, s.getY());
    }
}
