package Interface;
public class ComputerDell extends ComputerBase {
    public ComputerDell() {
        System.out.println("OLD NAME: " + getName());
        setName("Computer Dell");
        System.out.println("NAME: " + getName());
    }

    @Override
    public void playGame(String gameName) {
        System.out.println("NEW CODE: " + getName() + " - " + gameName);
    }
}
