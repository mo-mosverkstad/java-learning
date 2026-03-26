package DesignPatterns.BehavioralPatterns.Memento;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<DocumentMemento> mementos;

    public History() {
        this.mementos = new ArrayList<>();
    }

    public void pushMemento(DocumentMemento memento) {
        this.mementos.add(memento);
    }

    public DocumentMemento popMemento() {
        this.mementos.remove(mementos.size() - 1);
        return this.mementos.get(mementos.size() - 1);
    }
}
