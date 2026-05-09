package se.inheritancecomposition;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.*;

class CompositeProtocolInterpreterAdapterTest {
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = File.createTempFile("composite-test", ".bin");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(testFile))) {
            String title = "TestTitle";
            String description = "TestDescription";
            out.writeByte(title.length());
            out.writeBytes(title);
            out.writeShort(description.length());
            out.writeBytes(description);
        }
    }

    @AfterEach
    void tearDown() {
        testFile.delete();
    }

    @Test
    void testReadProtocolReturnsCorrectTitleAndDescription() throws IOException {
        try (CompositeProtocolInterpreterAdapter adapter = new CompositeProtocolInterpreterAdapter(testFile)) {
            MessageProtocol protocol = adapter.readProtocol();
            assertEquals("TestTitle", protocol.getTitle());
            assertEquals("TestDescription", protocol.getDescription());
        }
    }

    @Test
    void testBytesReadIsExactlyCorrect() throws IOException {
        try (CompositeProtocolInterpreterAdapter adapter = new CompositeProtocolInterpreterAdapter(testFile)) {
            MessageProtocol protocol = adapter.readProtocol();
            int expectedTotal = "TestTitle".length() + "TestDescription".length();
            assertEquals(expectedTotal, protocol.getBytesRead(),
                    "bytesRead should exactly equal the sum of title + description lengths");
        }
    }

    @Test
    void testReadProtocolFromEmptyFileReturnsDefault() throws IOException {
        File emptyFile = File.createTempFile("empty-test", ".bin");
        try (CompositeProtocolInterpreterAdapter adapter = new CompositeProtocolInterpreterAdapter(emptyFile)) {
            MessageProtocol protocol = adapter.readProtocol();
            assertEquals("No title", protocol.getTitle());
            assertEquals("No description", protocol.getDescription());
        } finally {
            emptyFile.delete();
        }
    }

    @Test
    void testFileNotFoundThrowsException() {
        File nonExistent = new File("nonexistent-file.bin");
        assertThrows(FileNotFoundException.class, () -> new CompositeProtocolInterpreterAdapter(nonExistent));
    }

    @Test
    void testIsNotSubclassOfRandomAccessFile() {
        assertFalse(RandomAccessFile.class.isAssignableFrom(CompositeProtocolInterpreterAdapter.class));
    }

    @Test
    void testCloseDoesNotThrow() throws IOException {
        CompositeProtocolInterpreterAdapter adapter = new CompositeProtocolInterpreterAdapter(testFile);
        assertDoesNotThrow(adapter::close);
    }
}
