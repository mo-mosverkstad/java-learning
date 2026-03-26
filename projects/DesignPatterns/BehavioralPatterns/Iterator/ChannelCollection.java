package DesignPatterns.BehavioralPatterns.Iterator;

import java.util.List;

public interface ChannelCollection {

    void addChannel(Channel channel);
    List<Channel> getChannels();
    ChannelIterator getChannelIterator();

}
