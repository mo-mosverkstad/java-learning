package DesignPatterns.BehavioralPatterns.Mediator;

import java.util.UUID;

public class CommercialAirplane implements Airplane {
    private AirTrafficControlTower mediator;
    private int priority = 1;
    private final String id = UUID.randomUUID().toString();

    public CommercialAirplane(AirTrafficControlTower mediator) {
        this.mediator = mediator;
    }

    @Override
    public void requestTakeoff() {
        mediator.requestTakeoff(this);
    }

    @Override
    public void requestLanding() {
        mediator.requestLanding(this);
    }

    @Override
    public void notifyAirTrafficControl(String message) {
        System.out.println("Commercial Airplane: " + message);
    }

    public CommercialAirplane setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getId() {
        return id;
    }
}
