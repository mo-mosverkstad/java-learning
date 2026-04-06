package se.ebikerepair.util;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.DiagnosticTaskDTO;

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
        assertNotNull(tasks.get(0).name());
        assertNotNull(tasks.get(0).cost());
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
        assertNotNull(first.name());
        assertNotNull(first.telephoneNumber());
        assertNotNull(first.emailAddress());
        assertNotNull(first.bikes());
    }
}
