package Lambda;

public class GreetingClass implements GreetingInterface {
    @Override
    public int greet(String name) {
        System.out.println(String.format("Hello, %s!", name));
        return 0;
    }

}
