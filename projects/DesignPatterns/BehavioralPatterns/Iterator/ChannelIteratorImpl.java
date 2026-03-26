package DesignPatterns.BehavioralPatterns.Iterator;

public class ChannelIteratorImpl implements ChannelIterator {

    private ChannelCollection channelCollection;
    private int index = 0;

    public ChannelIteratorImpl(ChannelCollection channelCollection) {
        this.channelCollection = channelCollection;
    }

    @Override
    public boolean hasNext() {
        return index < channelCollection.getChannels().size();
    }

    @Override
    public Channel next() {
        return channelCollection.getChannels().get(index++);
    }

    @Override
    public void reset() {
        index = 0;
    }

}
