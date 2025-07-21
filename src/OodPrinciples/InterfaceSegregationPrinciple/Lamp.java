package OodPrinciples.InterfaceSegregationPrinciple;

public class Lamp implements Electrifable {
    @Override
    public void powerOn() {
        System.out.println("Lamp is on");
    }

    @Override
    public void powerOff() {
        System.out.println("Lamp is off");
    }

    @Override
    public float getPrice() {
        return 100.0f;
    }

}
