package se.ebikerepair.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.Printer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ViewTest {
    private View view;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        RegistryCreator registryCreator = new RegistryCreator();
        ControllerCreator controllerCreator = new ControllerCreator(registryCreator, new Printer());
        view = new View(controllerCreator);

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testConstructor() {
        assertNotNull(view);
    }

    @Test
    void testProceedActionsFullWorkflow() {
        view.proceedActions("0707654321", "MO-2024-010");
        String output = outputStream.toString();

        assertTrue(output.contains("1. Reception - Found customer:"));
        assertTrue(output.contains("Astrid Johansson"));
        assertTrue(output.contains("2. Reception - Created repair order."));
        assertTrue(output.contains("3. Technician - Requested repair order:"));
        assertTrue(output.contains("7. Reception - found repair order:"));
        assertTrue(output.contains("8. Reception - Accepted order"));
    }

    @Test
    void testProceedActionsWithAcceptedState() {
        view.proceedActions("0707654321", "MO-2024-010");
        String output = outputStream.toString();

        assertTrue(output.contains("Accepted"));
    }

    @Test
    void testProceedActionsInvalidPhone() {
        view.proceedActions("invalid", "MO-2024-010");
        String output = outputStream.toString();

        assertTrue(output.contains("Error:"));
    }

    @Test
    void testProceedActionsCustomerNotFound() {
        view.proceedActions("0700000000", "MO-2024-010");
        String output = outputStream.toString();

        assertTrue(output.contains("Error:"));
    }

    @Test
    void testProceedActionsInvalidBikeSerial() {
        view.proceedActions("0707654321", "NONEXISTENT");
        String output = outputStream.toString();

        assertTrue(output.contains("1. Reception - Found customer:"));
    }

    @Test
    void testProceedActionsDiagnosticUpdate() {
        view.proceedActions("0707654321", "MO-2024-010");
        String output = outputStream.toString();

        assertTrue(output.contains("4. Technician - Updated diagnostic task:"));
    }

    @Test
    void testProceedActionsProposedRepairTasks() {
        view.proceedActions("0707654321", "MO-2024-010");
        String output = outputStream.toString();

        assertTrue(output.contains("5. Technician - Created proposed repair tasks 01:"));
        assertTrue(output.contains("6. Technician - Created proposed repair tasks 02:"));
        assertTrue(output.contains("Replace Chain"));
        assertTrue(output.contains("Replace gears"));
    }
}
