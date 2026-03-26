package DesignPatterns.StructuralPatterns.Decorator;

public interface DataSource {
    void writeData(String data);
    String readData();
}
