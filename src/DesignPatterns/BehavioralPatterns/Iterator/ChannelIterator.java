package DesignPatterns.BehavioralPatterns.Iterator;

public interface ChannelIterator {

    boolean hasNext();
    Channel next();
    void reset();
}
