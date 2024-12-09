import java.util.Scanner;

class TextUI {
    private final Scanner scanner = new Scanner(System.in);

    public int getMatrixSize() {
        int N;
        while (true) {
            System.out.print("Enter N (2-8): ");
            N = scanner.nextInt();
            if (N >= 2 && N <= 8) {
                break;
            }
            System.out.println("N must be between 2 and 8.");
        }
        return N;
    }

    public double[][] getMatrixValues(int N) {
        double[][] matrix = new double[N][N];
        System.out.println("Enter the matrix values (0-255):");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                while (true) {
                    System.out.print("Enter value for matrix[" + i + "][" + j + "]: ");
                    matrix[i][j] = scanner.nextInt();
                    if (matrix[i][j] >= 0 && matrix[i][j] <= 255) {
                        break;
                    }
                    System.out.println("Value must be between 0 and 255.");
                }
            }
        }
        return matrix;
    }
    public void printMatrix(String message, double[][] matrix) {
        System.out.println(message);
        for (double[] row : matrix) {
            for (double value : row) {
//                System.out.print(Math.round(value) + " ");
                System.out.printf("%.2f" + " ", value);
            }
            System.out.println();
        }
    }
}