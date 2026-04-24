package se.ebikerepair.data;

import org.junit.jupiter.api.Test;

import se.ebikerepair.data.DataHandler;
import se.ebikerepair.data.DataHandlerFactory;
import se.ebikerepair.data.DatabaseHandler;
import se.ebikerepair.data.JsonFileHandler;

import static org.junit.jupiter.api.Assertions.*;

public class DataHandlerFactoryTest {
    @Test
    void testCreateJsonFileHandler() {
        DataHandler dataHandler = assertDoesNotThrow(() ->
            DataHandlerFactory.create("se.ebikerepair.data.JsonFileHandler")
        );
        assertEquals(JsonFileHandler.class, dataHandler.getClass());
    }

    @Test
    void testCreateDatabaseHandler() {
        DataHandler dataHandler = assertDoesNotThrow(() ->
            DataHandlerFactory.create("se.ebikerepair.data.DatabaseHandler")
        );
        assertEquals(DatabaseHandler.class, dataHandler.getClass());
    }

    @Test
    void testCreateNonexistent() {
        assertThrows(ClassNotFoundException.class, () -> {
            DataHandler dataHandler = DataHandlerFactory.create("nonexistentclass");
        });
    }
}
