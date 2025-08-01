package DesignPatterns.StructuralPatterns.Flyweight;

import java.awt.*;

public class TreeType {
    private static final String FORMAT = "TreeType: name=%s, color=%s, otherTreeData=%s";

    private String name;
    private Color color;
    private String otherTreeData;

    public TreeType(String name, Color color, String otherTreeData) {
        this.name = name;
        this.color = color;
        this.otherTreeData = otherTreeData;
    }

    public void draw(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillRect(x - 1, y, 3, 5);
        g.setColor(color);
        g.fillOval(x - 5, y - 10, 10, 10);
    }

    @Override
    public String toString() {
        return String.format(FORMAT, name, color, otherTreeData);
    }
}
