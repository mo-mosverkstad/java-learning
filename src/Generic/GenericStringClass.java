package Generic;
public class GenericStringClass {

    private String value;
    private GenericStringClass next;

    public GenericStringClass(String value) {
        this.value = value;
    }

    public void setNext(GenericStringClass next) {
        this.next = next;
    }

    public GenericStringClass getNext() {
        return next;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
