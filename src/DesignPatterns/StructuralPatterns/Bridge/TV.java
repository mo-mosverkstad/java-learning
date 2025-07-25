package DesignPatterns.StructuralPatterns.Bridge;

public class TV implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 0;
    private String[] channels = {"SVT1", "SVT2", "TV3", "TV4", "TV5", "TV6", "Sjuan", "TV8", "Kanal9", "TV10", "Kunskapskanalen", "SVT24", "SVT Barn"}; 

    @Override
    public boolean isEnabled() {
        return on;
    }

    @Override
    public void enable() {
        on = true;
    }

    @Override
    public void disable() {
        on = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int volume) {
        if (volume > 100) {
            this.volume = 100;
        } else if (volume < 0) {
            this.volume = 0;
        } else {
            this.volume = volume;
        }
    }

    @Override
    public String getChannelName() {
        return channels[channel];
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        if (channel < 0) {
            channel = 0;
        } else if (channel > channels.length - 1) {
            channel = channels.length - 1;
        }
        this.channel = channel;
    }

    @Override
    public void printStatus() {
        System.out.println("------------------------------------");
        System.out.println("| I'm TV set.");
        System.out.println("| I'm " + (on ? "enabled" : "disabled"));
        System.out.println("| Current volume is " + volume + "%");
        System.out.println("| Current channel is " + channels[channel]);
        System.out.println("------------------------------------\n");
    }
}
