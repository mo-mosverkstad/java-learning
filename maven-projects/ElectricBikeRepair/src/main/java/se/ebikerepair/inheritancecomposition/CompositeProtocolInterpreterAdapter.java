package se.ebikerepair.inheritancecomposition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import se.ebikerepair.inheritancecomposition.MessageProtocol;

public class CompositeProtocolInterpreterAdapter {
    private final RandomAccessFile randomAccessFile;

    public CompositeProtocolInterpreterAdapter(File file) throws FileNotFoundException {
        randomAccessFile = new RandomAccessFile(file, "r");
    }

    public MessageProtocol readProtocol() {
        try {
            int titleLength = randomAccessFile.readUnsignedByte();
            byte[] titleBytes = new byte[titleLength];
            randomAccessFile.readFully(titleBytes);
            String title = new String(titleBytes);


            int descriptionLength = randomAccessFile.readUnsignedShort();
            byte[] descriptionBytes = new byte[descriptionLength];
            randomAccessFile.readFully(descriptionBytes);
            String description = new String(descriptionBytes);
            return new MessageProtocol(title, description);

        } catch (IOException e) {
            e.printStackTrace();
            return new MessageProtocol("No title", "No description");
        }
    }
}
