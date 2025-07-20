package DataTools;

interface Column {
    void append(Object value);
    Object get(int index);
    int size();
}