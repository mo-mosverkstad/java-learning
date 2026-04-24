package se.ebikerepair.data;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.ebikerepair.data.DatabaseHandler;
import se.ebikerepair.data.NoDatabaseException;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.DiagnosticTaskDTO;

public class DatabaseHandlerTest {
    @Test
    void testReadCustomers() {
        DatabaseHandler handler = new DatabaseHandler();
        assertThrows(NoDatabaseException.class, () -> handler.initDataHandler("customers"));
        assertThrows(NoDatabaseException.class, () -> handler.readList(CustomerDTO.class));
    }

    @Test
    void testReadDiagnosticTasksThrows() {
        DatabaseHandler handler = new DatabaseHandler();
        assertThrows(NoDatabaseException.class, () -> handler.initDataHandler("diagnosticTasks"));
        assertThrows(NoDatabaseException.class, () -> handler.readList(DiagnosticTaskDTO.class));
    }

    @Test
    void testReadNonExistentFile() {
        DatabaseHandler handler = new DatabaseHandler();
        assertThrows(NoDatabaseException.class, () -> handler.initDataHandler("nonexistent"));
    }
}
