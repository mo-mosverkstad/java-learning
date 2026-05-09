package se.ebikerepair.startup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testMainOutputsCustomerFound() {
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("1. Reception - Found customer:"));
    }

    @Test
    void testMainOutputsRepairOrderCreated() {
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("2. Reception - Created repair order."));
    }

    @Test
    void testMainOutputsTechnicianDiagnostic() {
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("3. Technician - Requested repair order:"));
        assertTrue(output.contains("4. Technician - Updated diagnostic task:"));
    }

    @Test
    void testMainOutputsRepairTasks() {
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("5. Technician - Created proposed repair tasks 01:"));
        assertTrue(output.contains("6. Technician - Created proposed repair tasks 02:"));
    }

    @Test
    void testMainOutputsAcceptedOrder() {
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("8. Reception - Accepted order"));
        assertTrue(output.contains("Accepted"));
    }

    @Test
    void testMainOutputsPrinterPrintout() {
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("Printing repair order FROM PRINTER"));
    }

    @Test
    void testMainOutputsObserverNotification() {
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("REPAIR ORDER MODIFICATION NOTIFICATION"));
        assertTrue(output.contains("THE REPAIR ORDER HAS BEEN MODIFIED"));
    }
}
