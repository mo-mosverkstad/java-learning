package TFIDF;

public class Triple<T, U, V> {

    private static final String FORMAT = "(%s, %s, %s)";
    private T left;
    private U middle;
    private V right;

    public Triple(T left, U middle, V right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public U getMiddle() {
        return middle;
    }

    public V getRight() {
        return right;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public void setMiddle(U middle) {
        this.middle = middle;
    }
    
    public void setRight(V right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, left, middle, right);
    }
}
