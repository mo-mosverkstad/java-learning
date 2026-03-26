package Lambda;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GreetingMain {

    public static void main(String[] args) {
        GreetingClass greeting = new GreetingClass();
        greeting.greet("Java");

        GreetingInterface greeting2 = new GreetingClass();
        greeting2.greet("Java");

        GreetingInterface greetingItaly = name -> {
            System.out.println(String.format("Ciao, %s!", name));
            return 0;
        };
        System.out.println(greetingItaly.greet("Java"));


        Predicate<Integer> isEven = i -> i % 2 == 0;
        System.out.println(isEven.test(2));

        Predicate<Integer> isOdd = n -> n % 2 != 0;
        System.out.println(isOdd.test(2));
        
        Consumer<String> greetingItaly2 = s -> {
            System.out.println(String.format("Ciao, %s!", s));
        };
        greetingItaly2.accept("Java");

        Supplier<String> helloSupplier = () -> {
            return "Hello, World!";
        };
        System.out.println(helloSupplier.get());

        ThreeArgsFunctionInterface<String, String, Integer, Boolean> checkTwoStringLength = (s1, s2, len) -> {
            return s1.length() == len && s2.length() == len;
        };
        System.out.println(checkTwoStringLength.apply("hello", "world", 5));
    }
}
