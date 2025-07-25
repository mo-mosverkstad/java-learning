package DesignPatterns.StructuralPatterns.Adapter;

public class AdapterMain {

    public static void main(String[] args) {
        RoundHole roundHole = new RoundHole(5);

        RoundPeg smallRoundPeg = new RoundPeg(50);
        fits(roundHole, smallRoundPeg);

        SquarePeg smallSquarePeg = new SquarePeg(8);
        SquarePegAdapter squarePegAdapter = new SquarePegAdapter(smallSquarePeg);
        fits(roundHole, squarePegAdapter);

    }

    private static boolean fits(RoundHole hole, RoundPeg peg) {
        boolean result = hole.fits(peg);
        System.out.println(result ? String.format("%s fits %s", peg, hole) : String.format("%s does not fit %s", peg, hole));
        return result;
    }
}
