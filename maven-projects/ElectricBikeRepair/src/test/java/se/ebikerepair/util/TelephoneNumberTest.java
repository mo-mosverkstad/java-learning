package se.ebikerepair.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.util.TelephoneNumber;

import static org.junit.jupiter.api.Assertions.*;

public class TelephoneNumberTest {

    @Test
    void testE164() {
        TelephoneNumber number = new TelephoneNumber("+46701234567");
        assertEquals("+46701234567", number.toE164());
    }

    @Test
    void testInternationalPrefix() {
        TelephoneNumber number = new TelephoneNumber("0046701234567");
        assertEquals("+46701234567", number.toE164());
    }

    @Test
    void testLocalPrefix() {
        TelephoneNumber number = new TelephoneNumber("0701234567");
        assertEquals("+46701234567", number.toE164());
    }

    @Test
    void testSpaced() {
        TelephoneNumber number = new TelephoneNumber(" 070-123 45 67 ");
        assertEquals("+46701234567", number.toE164());
    }

    @Test
    void testNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TelephoneNumber(null);
        });
    }

    @Test
    void testEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TelephoneNumber("   ");
        });
    }

    @Test
    void testNonZeroPrefix() {
        TelephoneNumber number = new TelephoneNumber("1234567");
        assertThrows(IllegalArgumentException.class, () -> {
            number.toE164();
        });
    }

    @Test
    void testWrongPrefix() {
        TelephoneNumber number = new TelephoneNumber("0001234567");
        assertThrows(IllegalArgumentException.class, () -> {
            number.toE164();
        });
    }
}
