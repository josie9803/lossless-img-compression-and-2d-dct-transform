class DCTModel {
    public double[][] applyDCT(double[][] matrix, int N, boolean rowFirst) {
        double[][] intermediate = new double[N][N];
        double[][] result = new double[N][N];

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
    private double[] compute1DDCT(double[] data, int N) {
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
    public double[] perform2DDCT() {
        double[][] coefficients = createTransformMatrix(8);
//        double[][] row1 = new double[][]{new double[]{100.0},
//                new double[]{110.0}, new double[]{120.0},
//                new double[]{130.0}, new double[]{140.0},
//                new double[]{150.0}, new double[]{160}, new double[]{170}};
        double[] row1 = {100.0, 110.0, 120.0, 130.0, 140.0, 150.0, 160.0, 170.0};
        return multiplyMatrixVector(coefficients, row1);
    }
//    public double[][] multiplyMatrices(double[][] matrixA, double[][] matrixB) {
//        int rowsA = matrixA.length;
//        int colsA = matrixA[0].length;
//        int rowsB = matrixB.length;
//        int colsB = matrixB[0].length;
//
//        if (colsA != rowsB) {
//            throw new IllegalArgumentException("Matrix multiplication is not possible: Columns of A must match rows of B.");
//        }
//
//        double[][] result = new double[rowsA][colsB];
//        for (int i = 0; i < rowsA; i++) {
//            for (int j = 0; j < colsB; j++) {
//                result[i][j] = 0;
//                for (int k = 0; k < colsA; k++) {
//                    result[i][j] += matrixA[i][k] * matrixB[k][j];
//                }
//            }
//        }
//        return result;
//    }
public double[][] transformMatrixRows(double[][] matrix) {
    int n = matrix.length;

    // Create the transform matrix
    double[][] transformMatrix = createTransformMatrix(n);

    // Result matrix after transformation
    double[][] transformedMatrix = new double[n][n];

    // Apply the transform to each row
    for (int i = 0; i < n; i++) {
        transformedMatrix[i] = multiplyMatrixVector(transformMatrix, matrix[i]);
    }

    return transformedMatrix;
}
    public double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        int n = matrix.length;
        if (vector.length != n) {
            throw new IllegalArgumentException("Matrix and vector dimensions do not match.");
        }
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = 0;
            for (int j = 0; j < n; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            if (Math.abs(result[i]) < 1e-10) {
                result[i] = 0;
            }
        }

        return result;
    }
    public double[][] createTransformMatrix(int N) {
        double[][] coefficients = new double[N][N];
        double sqrt1N = Math.sqrt(1.0 / N);
        double sqrt2N = Math.sqrt(2.0 / N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double a = (i == 0) ? sqrt1N : sqrt2N;
                coefficients[i][j] = a * Math.cos(((2 * j + 1) * i * Math.PI) / (2 * N));
            }
        }
        return coefficients;
    }
    public boolean compareMatrices(double[][] matrix1, double[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return false;
        }

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                if (Math.round(matrix1[i][j]) != Math.round(matrix2[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }
}
