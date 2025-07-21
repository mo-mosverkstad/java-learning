package OodPrinciples.LawOfDemeter;

public class Software {

    private Os os;
    private WebBrowser webBrowser;
    private Editor editor;

    public Software() {
        this.os = new Os("Windows 11");
        this.webBrowser = new WebBrowser("Google Chrome");
        this.editor = new Editor("Office 365");
    }
    public String toString() {
        return "Software" + "\n" + os + "\n" + webBrowser + "\n" + editor + "\n";
    }
}
