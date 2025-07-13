package Interface;
import java.lang.reflect.InvocationTargetException;

public class ComputerTest {
    private static String[] computers = {"ComputerHp", "ComputerDell"};

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        for (String name : computers) {
            testComputer(ComputerFactory.createComputer(name));
        }
    }

    public static void testComputer(ComputerInterface computer) {
        computer.powerOn();
        computer.playGame("League of Legends");
        computer.playGame("Dota 2");
        computer.powerOff();
    }
}
