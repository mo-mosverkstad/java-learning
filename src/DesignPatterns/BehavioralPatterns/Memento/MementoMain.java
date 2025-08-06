package DesignPatterns.BehavioralPatterns.Memento;

public class MementoMain {

    public static void main(String[] args) {
        Document document = new Document("Initial content\n");
        History history = new History();

        printDocument(document, "Initial state");

        // Write some content
        document.write("Additional content\n");
        history.pushMemento(document.createMemento());

        printDocument(document, "After additional content");

        // Write more content
        document.write("More content\n");
        history.pushMemento(document.createMemento());

        printDocument(document, "After more content");

        // Restore to previous state
        document.restoreFromMemento(history.popMemento());

        printDocument(document, "After restore to previous state");
    }

    private static void printDocument(Document document, String message) {
        System.out.println("=================== " + message + " ===================");
        System.out.println(document.getContent());
    }
}
