package se.inheritancecomposition;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Entry point demonstrating how inheritance breaks encapsulation (Chapter 9.3).
 * <p>
 * The inheritance adapter overrides {@code read(byte[])} to track bytes read, but
 * {@code readFully()} in {@link java.io.RandomAccessFile} does NOT call {@code read(byte[])}
 * internally — it uses its own read loop. The override is silently bypassed, and bytesRead
 * stays at zero. The composition adapter has no such problem because it controls all calls
 * explicitly without relying on superclass internals.
 * </p>
 */
public class Main {

    /**
     * Runs the demo: creates a test binary file with two messages, then reads both
     * sequentially using each adapter to show the encapsulation difference.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        File testFile = createTestBinaryFile();
        System.out.println("--- Inheritance vs Composition Demo ---");
        System.out.println("(Each adapter overrides/wraps read() to track bytesRead)\n");

        System.out.println("[Inheritance adapter (extends RandomAccessFile)]:");
        System.out.println("  Overrides read(byte[]) to count bytes, but readFully()");
        System.out.println("  does NOT call read(byte[]) — the override is SILENTLY BYPASSED.\n");
        try (InheritedProtocolInterpreterAdapter inherited = new InheritedProtocolInterpreterAdapter(testFile)) {
            System.out.println("  First read:  " + inherited.readProtocol());
            System.out.println("  Second read: " + inherited.readProtocol());
        } catch (Exception e) {
            System.out.println("  Read failed: " + e.getMessage());
        }

        System.out.println("\n[Composition adapter (wraps RandomAccessFile)]:");
        System.out.println("  Counts bytes in its own readBytes() method — no reliance");
        System.out.println("  on superclass internals, so counting is always correct.\n");
        try (CompositeProtocolInterpreterAdapter composite = new CompositeProtocolInterpreterAdapter(testFile)) {
            System.out.println("  First read:  " + composite.readProtocol());
            System.out.println("  Second read: " + composite.readProtocol());
        } catch (Exception e) {
            System.out.println("  Read failed: " + e.getMessage());
        }

        System.out.println("\nConclusion:");
        System.out.println("  Inheritance: bytesRead = 0 (WRONG) because the superclass");
        System.out.println("    bypasses our overridden read(byte[]) — we cannot control");
        System.out.println("    which internal methods the superclass calls.");
        System.out.println("  Composition: bytesRead is CORRECT because we explicitly");
        System.out.println("    control all read calls without depending on internals.");
    }

    /**
     * Creates a binary file with two sample message protocols for testing.
     *
     * @return the created test file
     */
    static File createTestBinaryFile() {
        File file = new File("temp.bin");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            writeMessage(out, "BikeAlert", "Chain replacement needed");
            writeMessage(out, "BatteryAlert", "Battery replacement needed");
        } catch (IOException e) {
            System.out.println("Failed to create test file: " + e.getMessage());
        }
        return file;
    }

    private static void writeMessage(DataOutputStream out, String title, String description) throws IOException {
        out.writeByte(title.length());
        out.writeBytes(title);
        out.writeShort(description.length());
        out.writeBytes(description);
    }
}
