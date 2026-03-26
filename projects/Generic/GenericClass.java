package Generic;
public class GenericClass <T> {
    private T value;
    private GenericClass<T> next;

    public GenericClass(T value) {
        this.value = value;
    }

    public void setNext(GenericClass<T> next) {
        this.next = next;
    }

    public GenericClass<T> getNext() {
        return next;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
