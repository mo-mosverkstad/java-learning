package se.ebikerepair.startup;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    public void testAdd() {
        Main main = new Main();
        assertEquals(5, main.add(2, 3));
    }

    @Test
    public void testAddNegative() {
        Main main = new Main();
        assertEquals(-1, main.add(2, -3));
    }

    @Test
    public void testAddZero() {
        Main main = new Main();
        assertEquals(0, main.add(0, 0));
    }
}
