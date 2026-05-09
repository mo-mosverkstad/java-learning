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

class InheritedProtocolInterpreterAdapterTest {
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = File.createTempFile("inherited-test", ".bin");
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
        try (InheritedProtocolInterpreterAdapter adapter = new InheritedProtocolInterpreterAdapter(testFile)) {
            MessageProtocol protocol = adapter.readProtocol();
            assertEquals("TestTitle", protocol.getTitle());
            assertEquals("TestDescription", protocol.getDescription());
        }
    }

    @Test
    void testBytesReadIsZeroDueToBypassedOverride() throws IOException {
        // readFully() does NOT call our overridden read(byte[]),
        // so bytesRead stays at 0 — demonstrating encapsulation breakage
        try (InheritedProtocolInterpreterAdapter adapter = new InheritedProtocolInterpreterAdapter(testFile)) {
            MessageProtocol protocol = adapter.readProtocol();
            assertEquals(0, protocol.getBytesRead(),
                    "bytesRead should be 0 because readFully() bypasses the overridden read(byte[])");
        }
    }

    @Test
    void testReadProtocolFromEmptyFileReturnsDefault() throws IOException {
        File emptyFile = File.createTempFile("empty-test", ".bin");
        try (InheritedProtocolInterpreterAdapter adapter = new InheritedProtocolInterpreterAdapter(emptyFile)) {
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
        assertThrows(FileNotFoundException.class, () -> new InheritedProtocolInterpreterAdapter(nonExistent));
    }

    @Test
    void testIsInstanceOfRandomAccessFile() throws IOException {
        try (InheritedProtocolInterpreterAdapter adapter = new InheritedProtocolInterpreterAdapter(testFile)) {
            assertInstanceOf(RandomAccessFile.class, adapter);
        }
    }
}
