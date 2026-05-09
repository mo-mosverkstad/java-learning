package se.ebikerepair.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.MembershipDTO;
import se.ebikerepair.integration.ProblemDTO;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.DiagnosticReportDTO;
import se.ebikerepair.integration.RepairTaskCollectionDTO;
import se.ebikerepair.model.Cost;
import se.ebikerepair.model.PricingResult;
import se.ebikerepair.model.RepairOrderState;
import se.ebikerepair.model.AbstractRepairOrderObserver;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepairOrderLoggerTest {
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

    private RepairOrderDTO createTestRepairOrderDTO() {
        BikeDTO bike = new BikeDTO("Shimano", "E6100", "SN-001");
        MembershipDTO membership = new MembershipDTO(false, 0);
        CustomerDTO customer = new CustomerDTO("Test", "+46701234567", "t@t.com", membership, List.of(bike));
        ProblemDTO problem = new ProblemDTO("Broken", bike);
        Cost zeroCost = new Cost(0, "SEK");
        PricingResult pricingResult = new PricingResult(zeroCost, zeroCost, zeroCost);
        DiagnosticReportDTO diagReport = new DiagnosticReportDTO(0, zeroCost, "None", List.of());
        RepairTaskCollectionDTO taskCollection = new RepairTaskCollectionDTO(0, zeroCost, List.of());
        return new RepairOrderDTO(
                customer, problem, new Date(), null, pricingResult, zeroCost,
                RepairOrderState.NewlyCreated, diagReport, taskCollection, "id-1"
        );
    }

    @Test
    void testHandleErrorsOutputsErrorMessage() {
        // Create a test observer that simulates the same handleErrors behavior
        AbstractRepairOrderObserver failingLogger = new AbstractRepairOrderObserver() {
            @Override
            protected void doHandleRepairOrderUpdate() throws Exception {
                throw new Exception("Simulated log failure");
            }

            @Override
            protected void handleErrors(Exception e) {
                System.out.println("Failed to log repair order: " + e.getMessage());
            }
        };

        failingLogger.updateRepairOrder(createTestRepairOrderDTO());
        String output = outputStream.toString();
        assertTrue(output.contains("Failed to log repair order"));
        assertTrue(output.contains("Simulated log failure"));
    }

    @Test
    void testSuccessfulUpdateDoesNotPrintErrorToSystemOut() {
        RepairOrderLogger logger = new RepairOrderLogger();
        logger.updateRepairOrder(createTestRepairOrderDTO());
        String output = outputStream.toString();
        assertFalse(output.contains("Failed to log repair order"));
    }
}
