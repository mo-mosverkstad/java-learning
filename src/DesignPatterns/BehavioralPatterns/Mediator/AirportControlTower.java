package DesignPatterns.BehavioralPatterns.Mediator;

public class AirportControlTower implements AirTrafficControlTower {
    private int planesInAir = 0;
    private static final int MAX_PLANES = 2;
    @Override
    public void requestTakeoff(Airplane airplane) {
        // Logic for coordinating takeoff
        if (airplane.getPriority() <= 1 && planesInAir < MAX_PLANES) {
            airplane.notifyAirTrafficControl("Requesting takeoff clearance: " + airplane.getId());
            planesInAir++;
        } else {
            airplane.notifyAirTrafficControl("Requesting takeoff is denied: "+ airplane.getId());
        }
    }

    @Override
    public void requestLanding(Airplane airplane) {
        // Logic for coordinating landing
        if (airplane.getPriority() <= 1) {
            airplane.notifyAirTrafficControl("Requesting landing clearance: " + airplane.getId());
            planesInAir--;
        } else {
            airplane.notifyAirTrafficControl("Requesting landing is denied: "+ airplane.getId());
        }
    }
}
