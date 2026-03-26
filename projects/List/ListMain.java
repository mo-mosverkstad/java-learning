package List;

import java.util.Arrays;
import java.util.List;

public class ListMain {

    public static void main(String[] args) {

        String[] strings = { "harry", "bob", "david", "eve", "alice", "charlie", "frank", "george"};
        List<String> list = Arrays.asList(strings);
        list.sort((element1, element2) -> element1.compareTo(element2));
        list.forEach(element -> System.out.println(element));
        list.forEach(System.out::println);
    }
}