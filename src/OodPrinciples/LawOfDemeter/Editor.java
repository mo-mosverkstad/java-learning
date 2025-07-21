package OodPrinciples.LawOfDemeter;

public class Editor {

    private String name;

    public Editor(String name) {
        this.name = name;
    }

    public String toString() {
        return "Editor" + name;
    }
}
