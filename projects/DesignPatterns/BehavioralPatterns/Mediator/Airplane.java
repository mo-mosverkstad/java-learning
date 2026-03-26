package DesignPatterns.BehavioralPatterns.Mediator;

public interface Airplane {
    void requestTakeoff();
    void requestLanding();
    void notifyAirTrafficControl(String message);
    int getPriority();
    String getId();
}
