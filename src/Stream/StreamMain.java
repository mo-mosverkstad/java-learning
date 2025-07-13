package Stream;

import java.util.Arrays;
import java.util.List;

public class StreamMain {

    public static void main(String[] args) {
        String[] strings = { "harry", "bob", "david", "eve", "alice", "charlie", "frank", "george"};
        List<String> list = Arrays.asList(strings);
        list.stream().sorted().forEach(System.out::println);

        int factor = 2;
        Integer[] integers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        //Arrays.stream(integers).map(x -> x * x).forEach(System.out::println);
        int sum = Arrays
            .stream(integers)
            .peek(x -> System.out.println(String.format("before: %d", x)))
            .map(x -> x * x * factor)
            .peek(System.out::println)
            .mapToInt(x -> x)
            .sum();
        System.out.println("sum = " + sum);
    }
}
