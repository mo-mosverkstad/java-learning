package DesignPatterns.BehavioralPatterns.Mediator;

public class MediatorMain {

    public static void main(String[] args) {
        AirTrafficControlTower airTrafficControlTower = new AirportControlTower();
        CommercialAirplane commercialAirplane1 = new CommercialAirplane(airTrafficControlTower);
        CommercialAirplane commercialAirplane2 = new CommercialAirplane(airTrafficControlTower).setPriority(2);
        CommercialAirplane commercialAirplane3 = new CommercialAirplane(airTrafficControlTower);
        commercialAirplane1.requestTakeoff();
        commercialAirplane2.requestTakeoff();
        commercialAirplane3.requestTakeoff();
        commercialAirplane1.requestLanding();
        commercialAirplane3.requestTakeoff();
    }
}
