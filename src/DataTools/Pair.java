package DataTools;

public class Pair<T, U> {

    private final String FORMAT = "(%s, %s)";
    private T left;
    private U right;
    public Pair(T left, U right) {
        this.left = left;
        this.right = right;
    }
    public T getLeft() {
        return left;
    }
    public U getRight() {
        return right;
    }
    public void setLeft(T left) {
        this.left = left;
    }
    public void setRight(U right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, left, right);
    }
}
