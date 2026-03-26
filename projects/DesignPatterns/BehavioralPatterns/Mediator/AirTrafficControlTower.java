package DesignPatterns.BehavioralPatterns.Mediator;

public interface AirTrafficControlTower {
    void requestTakeoff(Airplane airplane);
    void requestLanding(Airplane airplane);
}
