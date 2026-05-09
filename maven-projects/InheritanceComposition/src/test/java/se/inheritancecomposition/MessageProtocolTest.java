package se.inheritancecomposition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageProtocolTest {

    @Test
    void testConstructorAndGetters() {
        MessageProtocol protocol = new MessageProtocol("title1", "desc1", 10);
        assertEquals("title1", protocol.getTitle());
        assertEquals("desc1", protocol.getDescription());
        assertEquals(10, protocol.getBytesRead());
    }

    @Test
    void testToString() {
        MessageProtocol protocol = new MessageProtocol("Hello", "World", 5);
        assertEquals("MessageProtocol(title = Hello, description = World, bytesRead = 5)", protocol.toString());
    }

    @Test
    void testZeroBytesRead() {
        MessageProtocol protocol = new MessageProtocol("A", "B", 0);
        assertEquals(0, protocol.getBytesRead());
    }
}
