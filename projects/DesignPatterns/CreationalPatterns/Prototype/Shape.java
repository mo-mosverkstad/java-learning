package DesignPatterns.CreationalPatterns.Prototype;

public interface Shape {

    Shape clone();
    boolean equals(Object obj);
    int getX();
    int getY();
    String getColor();
}