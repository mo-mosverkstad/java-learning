package DesignPatterns.BehavioralPatterns.Iterator;

public class IteratorMain {

    public static void main(String[] args) {

        testChannelIterator(new ChannelCollectionImpl());
    }

    private static ChannelIterator loadChannelCollection(ChannelCollection channelCollection) {
        channelCollection.addChannel(new Channel(94.0, "P2 Sprak Musik"));
        channelCollection.addChannel(new Channel(96.3, "Guldkanalen"));
        channelCollection.addChannel(new Channel(91.8, "Retro FM Skåne"));
        channelCollection.addChannel(new Channel(101.0, "Retro FM"));
        return channelCollection.getChannelIterator();
    }

    private static void printChannels(ChannelIterator channelIterator) {
        while (channelIterator.hasNext()) {
            Channel channel = channelIterator.next();
            System.out.println(channel);
        }
    }

    private static void testChannelIterator(ChannelCollection channelCollection) {
        printChannels(loadChannelCollection(channelCollection));
    }
}
