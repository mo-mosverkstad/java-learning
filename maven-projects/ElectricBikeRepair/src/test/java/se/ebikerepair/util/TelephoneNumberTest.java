package se.ebikerepair.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.util.TelephoneNumber;

import static org.junit.jupiter.api.Assertions.*;

public class TelephoneNumberTest {

    @Test
    void testE164() {
        assertDoesNotThrow(() -> {
            TelephoneNumber number = new TelephoneNumber("+46701234567");
            assertEquals("+46701234567", number.toE164());
        });
    }

    @Test
    void testInternationalPrefix() {
        assertDoesNotThrow(() -> {
            TelephoneNumber number = new TelephoneNumber("0046701234567");
            assertEquals("+46701234567", number.toE164());
        });
    }

    @Test
    void testLocalPrefix() {
        assertDoesNotThrow(() -> {
            TelephoneNumber number = new TelephoneNumber("0701234567");
            assertEquals("+46701234567", number.toE164());
        });
    }

    @Test
    void testSpaced() {
        assertDoesNotThrow(() -> {
            TelephoneNumber number = new TelephoneNumber(" 070-123 45 67 ");
            assertEquals("+46701234567", number.toE164());
        });
    }

    @Test
    void testNull() {
        assertThrows(NullPointerException.class, () -> {
            new TelephoneNumber(null);
        });
    }

    @Test
    void testEmptyInput() {
        assertThrows(InvalidTelephoneNumberException.class, () -> {
            new TelephoneNumber("   ");
        });
    }

    @Test
    void testNonZeroPrefix() {
        assertThrows(InvalidTelephoneNumberException.class, () -> {
            TelephoneNumber number = new TelephoneNumber("1234567");
            number.toE164();
        });
    }

    @Test
    void testWrongPrefix() {
        assertThrows(InvalidTelephoneNumberException.class, () -> {
            TelephoneNumber number = new TelephoneNumber("0001234567");
            number.toE164();
        });
    }
}
