package Basic;

public class App {
    public static void main(String... args) throws Exception {
        System.out.println("Hello, World!");

        Double d = 1.0 + 2.0;
        System.out.println(d);
        
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        App app = new App();
        int index = app.binarySearch(array, 9);
        //System.out.println(String.format("index: %d value: %d", index, array[index]));
        //printHello();

        int[] intArray = new int[10];
        intArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        app.checkArray(index, 0,1,2,3,4);
        app.changeArray(intArray, 2, 5);
        app.checkArray(index, intArray);
    }

    /**
     * Returns the position of the target in the array.
     *
     * @param array the sorted array.
     * @param target the object we're looking for.
     * @return the position of the target in the array.
     */
    private int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midValue = array[mid];

            if (midValue < target) {
                low = mid + 1;
            } else if (midValue > target) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -(low + 1);
    }

    public static void printHello() {
        System.out.println("Hello, World!");
    }

    public void checkArray(int index, int... args) {
        System.out.println(args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i] + ", ");
        }
        System.out.println();
    }

    public void changeArray(int[] args, int index, int value) {
        //args[index] = value;
        int [] array = new int[args.length];
        System.arraycopy(args, 0, array, 0, args.length);
        array[index] = value;

        checkArray(index, array);
    }
}