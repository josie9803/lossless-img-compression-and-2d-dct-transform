import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Read N and the input matrix
        int N;
        while (true) {
            System.out.print("Enter N (2-8): ");
            N = scanner.nextInt();
            if (N >= 2 && N <= 8) {
                break;
            }
            System.out.println("N must be between 2 and 8.");
        }

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

        // Step 2: Perform DCT row-first then column
        double[][] dctRowFirst = applyDCT(matrix, N, true);

        // Step 3: Perform DCT column-first then row
        double[][] dctColFirst = applyDCT(matrix, N, false);

        // Step 4: Print results
        System.out.println("DCT (Rows first, then columns):");
        printMatrix(dctRowFirst);

        System.out.println("DCT (Columns first, then rows):");
        printMatrix(dctColFirst);

    }

    private static double[][] applyDCT(double[][] matrix, int N, boolean rowFirst) {
        double[][] intermediate = new double[N][N];
        double[][] result = new double[N][N];

        // Apply 1D DCT row-wise or column-wise first
        if (rowFirst) {
            for (int i = 0; i < N; i++) {
                intermediate[i] = compute1DDCT(matrix[i], N);
            }
            for (int j = 0; j < N; j++) {
                double[] column = new double[N];
                for (int i = 0; i < N; i++) {
                    column[i] = intermediate[i][j];
                }
                double[] dctColumn = compute1DDCT(column, N);
                for (int i = 0; i < N; i++) {
                    result[i][j] = dctColumn[i];
                }
            }
        } else {
            for (int j = 0; j < N; j++) {
                double[] column = new double[N];
                for (int i = 0; i < N; i++) {
                    column[i] = matrix[i][j];
                }
                double[] dctColumn = compute1DDCT(column, N);
                for (int i = 0; i < N; i++) {
                    intermediate[i][j] = dctColumn[i];
                }
            }
            for (int i = 0; i < N; i++) {
                result[i] = compute1DDCT(intermediate[i], N);
            }
        }

        return result;
    }

    private static double[] compute1DDCT(double[] data, int N) {
        double[] result = new double[N];
        double alpha;

        for (int k = 0; k < N; k++) {
            alpha = (k == 0) ? Math.sqrt(1.0 / N) : Math.sqrt(2.0 / N);
            result[k] = 0;
            for (int n = 0; n < N; n++) {
                result[k] += data[n] * Math.cos(Math.PI * (n + 0.5) * k / N);
            }
            result[k] *= alpha;
        }

        return result;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(Math.round(value) + " ");
            }
            System.out.println();
        }
    }
}
