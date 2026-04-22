package se.ebikerepair.data;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.DiagnosticTaskDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileHandlerTest {

    private JsonFileHandler createHandler(String resourceName) {
        JsonFileHandler handler = new JsonFileHandler();
        handler.initDataHandler(resourceName);
        return handler;
    }

    @Test
    void testReadCustomers() {
        JsonFileHandler handler = createHandler("customers");
        List<CustomerDTO> customers = handler.readList(CustomerDTO.class);
        assertFalse(customers.isEmpty());
    }

    @Test
    void testReadDiagnosticTasks() {
        JsonFileHandler handler = createHandler("diagnosticTasks");
        List<DiagnosticTaskDTO> tasks = handler.readList(DiagnosticTaskDTO.class);
        assertFalse(tasks.isEmpty());
        assertNotNull(tasks.get(0).name());
        assertNotNull(tasks.get(0).cost());
    }

    @Test
    void testReadNonExistentFile() {
        assertThrows(ResourceNotFoundException.class, () -> {
            createHandler("nonexistent");
        });
    }

    @Test
    void testReadCustomerDetails() {
        JsonFileHandler handler = createHandler("customers");
        List<CustomerDTO> customers = handler.readList(CustomerDTO.class);
        CustomerDTO first = customers.get(0);
        assertNotNull(first.name());
        assertNotNull(first.telephoneNumber());
        assertNotNull(first.emailAddress());
        assertNotNull(first.bikes());
    }
}
