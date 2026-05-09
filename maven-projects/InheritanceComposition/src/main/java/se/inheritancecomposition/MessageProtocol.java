package se.inheritancecomposition;

/**
 * Represents a parsed binary message protocol containing a title and description.
 */
public class MessageProtocol {
    private final String title;
    private final String description;
    private final int bytesRead;

    /**
     * Creates a new message protocol with the given title, description, and bytes read count.
     *
     * @param title       the protocol message title
     * @param description the protocol message description
     * @param bytesRead   the total bytes read by the adapter at time of parsing
     */
    public MessageProtocol(String title, String description, int bytesRead) {
        this.title = title;
        this.description = description;
        this.bytesRead = bytesRead;
    }

    /**
     * Returns the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the total bytes read by the adapter at time of parsing.
     *
     * @return the bytes read count
     */
    public int getBytesRead() {
        return bytesRead;
    }

    @Override
    public String toString() {
        return String.format("MessageProtocol(title = %s, description = %s, bytesRead = %d)",
                title, description, bytesRead);
    }
}
