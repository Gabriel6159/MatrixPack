public class MatrixTest {
    public static void main(String[] args) {
        double[][] newArray = {
            {1, 2, 3},
            {5, 6, 7},
            {8, 9, 10},
            {13, 22, 18}
        };
        double[][] anotherArray = {
            {16, 10, 9, 15},
            {28, 6, 7, 20},
            {1, 2, 3, 4},
            {13, 22, 18, 14}
        };
        Matrix empty = new Matrix(0,0);
        Matrix matrixReloaded = new Matrix(anotherArray);
        Matrix newMatrix = new Matrix(newArray);
        newMatrix.setTo(matrixReloaded);
        newMatrix.transpose();
        newMatrix.constantMultiply(5);
        matrixReloaded.constantMultiply(-1);
        newMatrix.add(matrixReloaded);
        System.out.print(newMatrix.toString());
        System.out.print("TEST COMPLETE");
    }
}
