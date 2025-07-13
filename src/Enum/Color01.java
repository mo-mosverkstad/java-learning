package Enum;
public class Color01 {
    public static final Color01 Red = new Color01("Red", 0xFF0000);
    public static final Color01 Blue = new Color01("Blue", 0x0000FF);
    public static final Color01 Green = new Color01("Green", 0x00FF00);

    private String color;
    public int value;

    private Color01(String color, int value) {
        this.color = color;
        this.value = value;
    }
    public String name() {
        return color;
    }

    public String toString() {
        return name() + " : " + value;
    }

}
