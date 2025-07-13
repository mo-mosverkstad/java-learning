package Generic;
public class GenericObjectClass {

    private Object value;
    private GenericObjectClass next;

    public GenericObjectClass(Object value) {
        this.value = value;
    }

    public void setNext(GenericObjectClass next) {
        this.next = next;
    }

    public GenericObjectClass getNext() {
        return next;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        return value;
    }
}
