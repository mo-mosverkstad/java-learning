package Interface;
public class ComputerBase implements ComputerInterface {
    private String NAME = "Computer unknown";

    private static final String ACTION_FORMAT = "%s %s";
    protected static String POWER_ON = "Power On";
    private static final String POWER_OFF = "Power Off";

    public ComputerBase() {
        System.out.println("ComputerBase()");
    }

    @Override
    public void powerOn() {
        System.out.println(String.format(ACTION_FORMAT, NAME, POWER_ON));
    }

    @Override
    public void powerOff() {
        System.out.println(String.format(ACTION_FORMAT, NAME, POWER_OFF));
    }

    public void setName(String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

    @Override
    public void playGame(String gameName) {
        System.out.println("Play game: " + gameName);
    }
}
