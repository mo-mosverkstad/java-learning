package Enum;
public enum Color {

    Red(0xFF0000), Green(0x00FF00), Blue(0x0000FF), Yellow(0xFFFF00), Black(0x000000), White(0xFFFFFF), Purple(0xFF00FF), Orange(0xFFA500);

    public int value;
    private Color(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name() + " : " + value;
    }
}
