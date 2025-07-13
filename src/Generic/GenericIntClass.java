package Generic;
public class GenericIntClass {
    private int value = 0;
    private GenericIntClass next = null;

    public GenericIntClass(int value) {
        this.value = value;
    }

    public void setNext(GenericIntClass next) {
        this.next = next;
    }

    public GenericIntClass getNext() {
        return next;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
