package se.inheritancecomposition;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private final PrintStream originalOut = System.out;

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        new File("temp.bin").delete();
    }

    @Test
    void testCreateTestBinaryFileCreatesFileWithContent() {
        File file = Main.createTestBinaryFile();
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void testMainProducesExpectedOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Inheritance vs Composition Demo"));
        assertTrue(output.contains("Inheritance adapter"));
        assertTrue(output.contains("Composition adapter"));
        assertTrue(output.contains("BikeAlert"));
        assertTrue(output.contains("BatteryAlert"));
        assertTrue(output.contains("Chain replacement needed"));
        assertTrue(output.contains("Battery replacement needed"));
    }
}
