package OodPrinciples.LawOfDemeter;

public class WebBrowser {

    private String name;

    public WebBrowser(String name) {
        this.name = name;
    }
    public String toString() {
        return "WebBrowser: " + name;
    }
}
