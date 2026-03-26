package OodPrinciples.OpenClosedPrinciples;

public class Shape {

    private String shapeType;

    public Shape(String shapeType) {
        this.shapeType = shapeType;
    }

    public void draw() {
        System.out.println(String.format( "This is a simulated shape of a %s", shapeType));
        System.out.println(String.format("Drawing a %s", shapeType));
    }
}
