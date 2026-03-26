package OodPrinciples.LiskovSubstitutionPrinciple;

public class MatrixMain {

    public static void main(String[] args) {
        doMatrix(new MatrixArgument(args));
    }

    private static void doMatrix(MatrixConfig matrixConfig) {

        printMatrix(matrixConfig.getConfig(MatrixConfig.MATRIX_ENTRY),
                Integer.parseInt(matrixConfig.getConfig(MatrixConfig.MATRIX_WIDTH)),
                Integer.parseInt(matrixConfig.getConfig(MatrixConfig.MATRIX_HEIGHT))
                );
    }

    private static void printMatrix(String matrixEntry, int matrixWidth, int matrixHeight) {
        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                System.out.print(matrixEntry + " ");
            }
            System.out.println();
        }
    }
}
