package OodPrinciples.LawOfDemeter;

public class Peripherals {

    private Keyboard keyboard;
    private Mouse mouse;

    public Peripherals() {
        keyboard = new Keyboard();
        mouse = new Mouse();
    }

    public String toString() {
        return "Peripherals" + "\n" + keyboard + "\n" + mouse + "\n";
    }
}
