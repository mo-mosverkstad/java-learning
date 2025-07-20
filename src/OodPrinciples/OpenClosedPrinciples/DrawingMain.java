package OodPrinciples.OpenClosedPrinciples;

public class DrawingMain {

    public static void main(String[] args) {
        Drawing drawing = new Drawing();
        drawing.drawShape(new Circle());
        drawing.drawShape(new Rectangle());
        drawing.drawShape(new Ellipse());
    }
}
