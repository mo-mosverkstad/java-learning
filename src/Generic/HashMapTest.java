package Generic;
import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {
        HashMap<Integer, Person> map = new HashMap<>();
        map.put(1, new Person("Alice", 21));
        map.put(2, new Person("Bob", 22));
        map.put(3, new Person("Charlie", 13));
        
        printMap(map);

        Person person = map.get(1);
        System.out.println(person.setName("Alice New").setAge(36));
        System.out.println(new Person().setName("David").setAge(44));
    }

    public static void printMap(Map<Integer, Person> map) {
        for (Map.Entry<Integer, Person> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue() + " - " + entry.hashCode());
        }
    }
}
