package Generic;
public class GenericTest {

    public static void main(String[] args) {

        GenericObjectClass head = generateObjectChain(1, 2, "hello", 4, 5, new Person("Alice", 21));
        printObjectChain(head);

        GenericObjectClass head2 = generateObjectChain("Alice", "Bob", "Charlie", "David", "Eve");
        printObjectChain(head2);

        GenericClass<Integer> head3 = generateChain(1, 2, 3, 4, 5);
        printChain(head3);

        GenericClass<String> head4 = generateChain("Alice", "Bob", "Charlie", "David", "Eve");
        printChain(head4);

        GenericClass<Person> head5 = generateChain(new Person("Alice", 21),
        new Person("Bob", 22),
        new Person("Charlie", 13));
        printChain(head5);
    }

    public static GenericObjectClass generateObjectChain(Object... args) {
        GenericObjectClass head = new GenericObjectClass(args[0]);
        GenericObjectClass point = head;
        for (int i = 1; i < args.length; i++) {
            point.setNext(new GenericObjectClass(args[i]));
            point = point.getNext();
        }
        point.setNext(null);
        return head;
    }

    public static void printObjectChain(GenericObjectClass head) {
        GenericObjectClass point = head;
        while (point != null) {
            System.out.println(point.getValue());
            point = point.getNext();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> GenericClass<T> generateChain(T... args) {
        GenericClass<T> head = new GenericClass<>(args[0]);
        GenericClass<T> point = head;
        for (int i = 1; i < args.length; i++) {
            point.setNext(new GenericClass<T>(args[i]));
            point = point.getNext();
        }
        point.setNext(null);
        return head;
    }

    public static <E, T> void printChain(GenericClass<T> head) {
        GenericClass<T> point = head;
        while (point != null) {
            System.out.println(point.getValue());
            point = point.getNext();
        }
    }
}
