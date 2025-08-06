package DesignPatterns.BehavioralPatterns.Iterator;

import java.util.ArrayList;
import java.util.List;

public class ChannelCollectionImpl implements ChannelCollection {
    private List<Channel> channels = new ArrayList<>();
    private ChannelIterator iterator = new ChannelIteratorImpl(this);

    public List<Channel> getChannels() {
        return channels;
    }

    @Override
    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    @Override
    public ChannelIterator getChannelIterator() {
        return iterator;
    }
    
}
