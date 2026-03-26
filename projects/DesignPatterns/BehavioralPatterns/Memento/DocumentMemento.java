package DesignPatterns.BehavioralPatterns.Memento;

public class DocumentMemento implements Memento {
    private String content;

    public DocumentMemento(String content) {
        this.content = content;
    }

    @Override
    public String getSavedContent() {
        return this.content;
    }
}
