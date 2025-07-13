package Generic;

public class GenericVectorMain {

    public static void main(String[] args) {

        GenericVector<Integer> vector = new GenericVector<>();
        vector.setValues(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(vector);
    }
}
