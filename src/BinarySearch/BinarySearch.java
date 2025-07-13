package BinarySearch;

public class BinarySearch {
    public static void main(String[] args) throws Exception {       
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int index = binarySearch(array, 9);
        System.out.println(String.format("index: %d value: %d", index, array[index]));
    }
 
    /**
     * Returns the position of the target in the array.
     *
     * @param array the sorted array.
     * @param target the object we're looking for.
     * @return the position of the target in the array.
     */
    private static int binarySearch(int[] array, int target) {
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
}
