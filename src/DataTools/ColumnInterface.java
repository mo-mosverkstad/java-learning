package DataTools;

interface ColumnInterface {
    public void append();
    public void append(Object value);
    public void set(int index, Object value);
    public Object get(int index);
    public int size();
    public String getName();
    public boolean validate(Object value);
}