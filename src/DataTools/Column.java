package DataTools;

interface Column {
    public void append(Object value);
    public void set(int index, Object value);
    public Object get(int index);
    public int size();
}