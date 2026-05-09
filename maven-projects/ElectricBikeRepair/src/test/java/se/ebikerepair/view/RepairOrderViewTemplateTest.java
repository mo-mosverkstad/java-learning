package se.ebikerepair.view;

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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepairOrderViewTemplateTest {
    private RepairOrderViewTemplate viewTemplate;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        viewTemplate = new RepairOrderViewTemplate();
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
        CustomerDTO customer = new CustomerDTO("Anna Svensson", "+46707654321", "anna@test.com", membership, List.of(bike));
        ProblemDTO problem = new ProblemDTO("Broken chain", bike);
        Cost zeroCost = new Cost(0, "SEK");
        PricingResult pricingResult = new PricingResult(zeroCost, zeroCost, zeroCost);
        DiagnosticReportDTO diagReport = new DiagnosticReportDTO(0, zeroCost, "None", List.of());
        RepairTaskCollectionDTO taskCollection = new RepairTaskCollectionDTO(0, zeroCost, List.of());
        return new RepairOrderDTO(
                customer, problem, new Date(), null, pricingResult, zeroCost,
                RepairOrderState.NewlyCreated, diagReport, taskCollection, "test-order-123"
        );
    }

    @Test
    void testNotificationOutputsRepairOrderId() {
        RepairOrderDTO dto = createTestRepairOrderDTO();
        viewTemplate.updateRepairOrder(dto);
        String output = outputStream.toString();
        assertTrue(output.contains("test-order-123"));
    }

    @Test
    void testNotificationOutputsCustomerTelephone() {
        RepairOrderDTO dto = createTestRepairOrderDTO();
        viewTemplate.updateRepairOrder(dto);
        String output = outputStream.toString();
        assertTrue(output.contains("+46707654321"));
    }

    @Test
    void testNotificationOutputsCustomerName() {
        RepairOrderDTO dto = createTestRepairOrderDTO();
        viewTemplate.updateRepairOrder(dto);
        String output = outputStream.toString();
        assertTrue(output.contains("Anna Svensson"));
    }

    @Test
    void testNotificationOutputsModifiedMessage() {
        RepairOrderDTO dto = createTestRepairOrderDTO();
        viewTemplate.updateRepairOrder(dto);
        String output = outputStream.toString();
        assertTrue(output.contains("THE REPAIR ORDER HAS BEEN MODIFIED"));
    }

    @Test
    void testNotificationOutputsTelephoneCheckMessage() {
        RepairOrderDTO dto = createTestRepairOrderDTO();
        viewTemplate.updateRepairOrder(dto);
        String output = outputStream.toString();
        assertTrue(output.contains("PLEASE USE TELE NUMBER TO CHECK DETAILS"));
    }

    @Test
    void testHandleErrorsOutputsNullDTO() {
        viewTemplate.updateRepairOrder(null);
        String output = outputStream.toString();
        assertTrue(output.contains("ERROR:"));
        assertTrue(output.contains("No repair order DTO"));
    }
}
