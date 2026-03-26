package DesignPatterns.BehavioralPatterns.Iterator;

public record Channel (Double frequency, String name) {

    @Override
    public String toString() {
        return "Channel{" +
                "frequency=" + frequency +
                ", name='" + name + '\'' +
                '}';
    }
}
