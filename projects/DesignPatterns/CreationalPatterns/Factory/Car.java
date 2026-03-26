package DesignPatterns.CreationalPatterns.Factory;

public class Car implements RoadVehicle {

    @Override
    public String toString() {
        return "Car";
    }

    @Override
    public void driveOnRoad() {
        System.out.println("Car is driving on road");
    }

}
