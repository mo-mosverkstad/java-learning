package DesignPatterns.StructuralPatterns.Adapter;

public class SquarePegAdapter extends RoundPeg {

    private SquarePeg peg;
    private static final double SQRT_2_DIV_2 = (Math.sqrt(2) / 2);

    public SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
    }

    @Override
    public double getRadius() {
        double result = SQRT_2_DIV_2 * peg.getWidth();
        return result;
    }

    @Override
    public String toString() {
        return "SquarePegAdapter{" +
                "peg=" + peg +
                '}';
    }
}
