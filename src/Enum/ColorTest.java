package Enum;
public class ColorTest {
    public static void main(String[] rgs) {
        // It is illegal to create an instance with private constructor
        // Color01 red = new Color01("Red", 0xFF0000);
        
        Color01 myPenColor = Color01.Blue;
        System.out.println(myPenColor);

        Color myColor = Color.Blue;
        System.out.println(myColor);
        System.out.println(myColor.name() + " : " + myColor.value);
    }
}
