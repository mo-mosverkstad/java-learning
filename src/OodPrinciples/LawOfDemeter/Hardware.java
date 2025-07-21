package OodPrinciples.LawOfDemeter;

public class Hardware {

    private Power power;
    private MotherBoard motherBoard;
    private Cpu cpu;
    private HardDisk hardDisk;
    private Memory memory;

    public Hardware() {
        power = new Power();
        motherBoard = new MotherBoard();
        cpu = new Cpu();
        hardDisk = new HardDisk();
        memory = new Memory();
    }
    public String toString() {
        return "Hardware" + "\n" + power + "\n" + motherBoard + "\n" + cpu + "\n" + hardDisk + "\n" + memory + "\n";
    }
}
