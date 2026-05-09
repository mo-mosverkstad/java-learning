package se.ebikerepair.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.model.RepairOrder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {
    private Printer printer;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        printer = new Printer();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private RepairOrder createTestRepairOrder() {
        BikeDTO bike = new BikeDTO("Shimano", "E6100", "SN-001");
        MembershipDTO membership = new MembershipDTO(false, 0);
        CustomerDTO customer = new CustomerDTO("Test Customer", "+46701234567", "test@test.com", membership, List.of(bike));
        return new RepairOrder(customer);
    }

    @Test
    void testPrintOutputsHeader() {
        RepairOrder repairOrder = createTestRepairOrder();
        printer.print(repairOrder);
        String output = outputStream.toString();
        assertTrue(output.contains("Printing repair order FROM PRINTER"));
    }

    @Test
    void testPrintOutputsRepairOrderContent() {
        RepairOrder repairOrder = createTestRepairOrder();
        printer.print(repairOrder);
        String output = outputStream.toString();
        assertTrue(output.contains("Test Customer"));
        assertTrue(output.contains("+46701234567"));
    }

    @Test
    void testPrintOutputsRepairOrderId() {
        RepairOrder repairOrder = createTestRepairOrder();
        printer.print(repairOrder);
        String output = outputStream.toString();
        assertTrue(output.contains(repairOrder.getId()));
    }
}
