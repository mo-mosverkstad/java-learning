package se.ebikerepair.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class LogHandlerTest {

    @Test
    void testGetLoggerReturnsSameInstance() {
        LogHandler first = LogHandler.getLogger();
        LogHandler second = LogHandler.getLogger();
        assertSame(first, second);
    }

    @Test
    void testGetLoggerNotNull() {
        assertNotNull(LogHandler.getLogger());
    }

    @Test
    void testLogExceptionWritesToFile() throws IOException {
        LogHandler logger = LogHandler.getLogger();
        String uniqueMessage = "Test exception " + System.nanoTime();
        Exception testException = new Exception(uniqueMessage);

        logger.logException(testException);

        File logFile = new File("ebikerepair.log");
        assertTrue(logFile.exists());
        String content = Files.readString(logFile.toPath());
        assertTrue(content.contains(uniqueMessage));
        assertTrue(content.contains("Exception was thrown:"));
    }
}
