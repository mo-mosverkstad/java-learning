package DesignPatterns.StructuralPatterns.Bridge;

public class DeviceMain {

    public static void main(String[] args) {
        playDevice(new TV());
        playDevice(new Radio());

        playDevice(new Remote(new TV()));
        playDevice(new Remote(new Radio()));
    }

    private static void playDevice(Device device) {
        device.enable();
        device.setVolume(50);
        device.setChannel(10);
        device.printStatus();
        device.disable();
    }

    private static void playDevice(Remote remote) {
        remote.power();
        remote.volumeUp();
        remote.channelUp();
        remote.printStatus();
        remote.volumeDown();
        remote.channelDown();
        remote.printStatus();
        remote.mute();
        remote.printStatus();
        remote.power();
    }
}
