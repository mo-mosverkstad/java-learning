package OodPrinciples.InterfaceSegregationPrinciple;

public class EquipmentMain {

    public static void main(String[] args) {

        Electrifable tv = new TV();
        Displayable tv2 = new TV();
        Osable tv3 = new TV();
        runEquipment(tv);
        runEquipment(tv2);
        runEquipment(tv3);

        Electrifable lamp = new Lamp();
        runEquipment(lamp);
    }

    private static void runEquipment(Electrifable equipment) {

        equipment.powerOn();
        equipment.powerOff();
        System.out.println(equipment.getPrice());
    }

    private static void runEquipment(Displayable equipment) {
        System.out.println(equipment.display());
    }

    private static void runEquipment(Osable equipment) {
        System.out.println(equipment.getOsInfo());
        System.out.println(equipment.getOsVersion());
    }

}
