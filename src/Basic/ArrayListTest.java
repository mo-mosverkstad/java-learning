package Basic;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayListTest {

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        printList(list1);

        List<Integer> list2 = new LinkedList<>();
        list2.add(1);
        printList(list2);

        // https://medium.com/codex/understanding-hashmap-in-java-4bb2f839adea
    }

    public static void printList(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void printList2(List<Integer> list) {
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
