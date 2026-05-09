package se.inheritancecomposition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Adapts {@link RandomAccessFile} using inheritance to read a custom binary message protocol.
 * <p>
 * This adapter extends RandomAccessFile and overrides {@code read(byte[])} to track bytes read.
 * However, {@code readFully()} does NOT internally call {@code read(byte[])} — it uses its own
 * internal read loop. This means the overridden method is silently bypassed, and the bytesRead
 * counter stays at zero. This demonstrates how inheritance breaks encapsulation (Chapter 9.3):
 * the subclass cannot know or control which internal methods the superclass uses.
 * </p>
 */
public class InheritedProtocolInterpreterAdapter extends RandomAccessFile {
    private int bytesRead = 0;

    /**
     * Creates an adapter that reads from the specified file.
     *
     * @param file the binary protocol file to read
     * @throws FileNotFoundException if the file does not exist
     */
    public InheritedProtocolInterpreterAdapter(File file) throws FileNotFoundException {
        super(file, "r");
    }

    /**
     * Overrides {@code read(byte[])} to count bytes read.
     * <p>
     * The subclass expects this to be called by {@code readFully()}, but the superclass
     * bypasses it — demonstrating that inheritance exposes you to undocumented internal behavior.
     * </p>
     */
    @Override
    public int read(byte[] b) throws IOException {
        bytesRead += b.length;
        return super.read(b);
    }

    /**
     * Reads a {@link MessageProtocol} from the binary file.
     * <p>
     * The binary format is: 1 byte title length, title bytes, 2 byte description length, description bytes.
     * Uses {@code readFully()} which does NOT call the overridden {@code read(byte[])},
     * so bytesRead remains zero — an incorrect result caused by inheritance.
     * </p>
     *
     * @return the parsed message protocol, or a default protocol if an I/O error occurs
     */
    public MessageProtocol readProtocol() {
        try {
            int titleLength = readUnsignedByte();
            byte[] titleBytes = new byte[titleLength];
            readFully(titleBytes);
            String title = new String(titleBytes);

            int descriptionLength = readUnsignedShort();
            byte[] descriptionBytes = new byte[descriptionLength];
            readFully(descriptionBytes);
            String description = new String(descriptionBytes);
            return new MessageProtocol(title, description, bytesRead);

        } catch (IOException e) {
            return new MessageProtocol("No title", "No description", bytesRead);
        }
    }
}
