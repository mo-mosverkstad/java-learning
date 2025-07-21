package OodPrinciples.DependencyInversionPrinciple;

public class MatrixMain {

    public static void main(String[] args) {
        doMatrix(new MatrixConfig());
    }

    private static void doMatrix(MatrixConfigable matrix) {

        printMatrix(matrix.getConfig(MatrixConstances.MATRIX_ENTRY),
                Integer.parseInt(matrix.getConfig(MatrixConstances.MATRIX_WIDTH)),
                Integer.parseInt(matrix.getConfig(MatrixConstances.MATRIX_HEIGHT))
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
