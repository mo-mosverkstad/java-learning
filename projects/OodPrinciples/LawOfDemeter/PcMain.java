package OodPrinciples.LawOfDemeter;

public class PcMain {

    public static void main(String[] args) {

        buildPc(new Hardware(), new Peripherals(), new Software());
    }

    private static void buildPc(Hardware hardware, Peripherals peripherals, Software software) {
        System.out.println(hardware);
        System.out.println(peripherals);
        System.out.println(software);
    }
}
