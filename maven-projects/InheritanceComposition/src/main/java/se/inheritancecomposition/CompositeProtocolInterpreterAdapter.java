package se.inheritancecomposition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Adapts {@link RandomAccessFile} using composition to read a custom binary message protocol.
 * <p>
 * This adapter wraps a RandomAccessFile instance as a private field, exposing only the
 * protocol-reading functionality. Because no methods are overridden, there is no risk of
 * hidden internal calls corrupting state — demonstrating the safety of composition (Chapter 9.3).
 * </p>
 */
public class CompositeProtocolInterpreterAdapter implements AutoCloseable {
    private final RandomAccessFile randomAccessFile;
    private int bytesRead = 0;

    /**
     * Creates an adapter that reads from the specified file.
     *
     * @param file the binary protocol file to read
     * @throws FileNotFoundException if the file does not exist
     */
    public CompositeProtocolInterpreterAdapter(File file) throws FileNotFoundException {
        randomAccessFile = new RandomAccessFile(file, "r");
    }

    /**
     * Reads a {@link MessageProtocol} from the binary file.
     * <p>
     * The binary format is: 1 byte title length, title bytes, 2 byte description length, description bytes.
     * </p>
     *
     * @return the parsed message protocol, or a default protocol if an I/O error occurs
     */
    public MessageProtocol readProtocol() {
        try {
            int titleLength = randomAccessFile.readUnsignedByte();
            byte[] titleBytes = new byte[titleLength];
            readBytes(titleBytes);
            String title = new String(titleBytes);

            int descriptionLength = randomAccessFile.readUnsignedShort();
            byte[] descriptionBytes = new byte[descriptionLength];
            readBytes(descriptionBytes);
            String description = new String(descriptionBytes);
            return new MessageProtocol(title, description, bytesRead);

        } catch (IOException e) {
            return new MessageProtocol("No title", "No description", bytesRead);
        }
    }

    private void readBytes(byte[] b) throws IOException {
        randomAccessFile.readFully(b);
        bytesRead += b.length;
    }

    /**
     * Closes the underlying RandomAccessFile.
     *
     * @throws IOException if an I/O error occurs while closing
     */
    @Override
    public void close() throws IOException {
        randomAccessFile.close();
    }
}
