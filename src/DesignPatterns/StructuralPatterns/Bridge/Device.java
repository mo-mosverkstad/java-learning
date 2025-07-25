package DesignPatterns.StructuralPatterns.Bridge;

public interface Device {
    boolean isEnabled();
    void enable();
    void disable();
    int getVolume();
    void setVolume(int percent);
    int getChannel();
    String getChannelName();
    void setChannel(int channel);
    void printStatus();

}
