package se.ebikerepair.util;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.DiagnosticTaskDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileHandlerTest {

    @Test
    void testReadCustomers() {
        JsonFileHandler handler = new JsonFileHandler("customers.json");
        List<CustomerDTO> customers = handler.readList(CustomerDTO.class);
        assertFalse(customers.isEmpty());
    }

    @Test
    void testReadDiagnosticTasks() {
        JsonFileHandler handler = new JsonFileHandler("diagnosticTasks.json");
        List<DiagnosticTaskDTO> tasks = handler.readList(DiagnosticTaskDTO.class);
        assertFalse(tasks.isEmpty());
        assertNotNull(tasks.get(0).getName());
        assertNotNull(tasks.get(0).getCost());
    }

    @Test
    void testReadNonExistentFile() {
        JsonFileHandler handler = new JsonFileHandler("nonexistent.json");
        List<CustomerDTO> result = handler.readList(CustomerDTO.class);
        assertTrue(result.isEmpty());
    }

    @Test
    void testReadCustomerDetails() {
        JsonFileHandler handler = new JsonFileHandler("customers.json");
        List<CustomerDTO> customers = handler.readList(CustomerDTO.class);
        CustomerDTO first = customers.get(0);
        assertNotNull(first.getName());
        assertNotNull(first.getTelephoneNumber());
        assertNotNull(first.getEmailAddress());
        assertNotNull(first.getBikes());
    }
}
