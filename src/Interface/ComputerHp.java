package Interface;
public class ComputerHp extends ComputerBase {

    public ComputerHp() {
        setName("Computer Hp");
        System.out.println("NAME: " + getName());
    }
    @Override
    public void playGame(String gameName) {
        System.out.println(getName() + " - Play game: " + gameName);
    }
}
