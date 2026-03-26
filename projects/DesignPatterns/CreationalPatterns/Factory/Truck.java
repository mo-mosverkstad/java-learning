package DesignPatterns.CreationalPatterns.Factory;

public class  Truck implements RoadVehicle {
    @Override
    public void driveOnRoad() {
        System.out.println("Truck is driving on road");
    }

    @Override
    public String toString() {
        return "Truck";
    }

}
