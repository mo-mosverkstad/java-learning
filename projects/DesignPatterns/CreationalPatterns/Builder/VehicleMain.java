package DesignPatterns.CreationalPatterns.Builder;

public class VehicleMain {

    public static void main(String[] args) {
        Car car = new VehicleDirector<Car>().build("minicar");
        System.out.println(car);
        
        Car sportsCar = new VehicleDirector<Car>().build("sportsCar");
        System.out.println(sportsCar);

        Trunk trunk = new VehicleDirector<Trunk>().build("Toyota Motors");
        System.out.println(trunk);
    }
}
