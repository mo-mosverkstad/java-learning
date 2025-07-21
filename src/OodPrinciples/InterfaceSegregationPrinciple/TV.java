package OodPrinciples.InterfaceSegregationPrinciple;

public class TV implements Electrifable,Displayable,Osable {

    @Override
    public String display() {
        return "TV";
    }

    @Override
    public void powerOn() {
        System.out.println("TV is on");
    }

    @Override
    public void powerOff() {
        System.out.println("TV is off");
    }

    @Override
    public float getPrice() {
        return 13000.0f;
    }

    @Override
    public String getOsInfo() {
        return "Linux";
    }

    @Override
    public String getOsVersion() {
        return "6.16-rc7";
    }

}
