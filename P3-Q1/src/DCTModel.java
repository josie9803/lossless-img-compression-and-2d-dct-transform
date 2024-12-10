class DCTModel {

public double[][] perform2DDCT(double[][] inputMatrix, boolean isRowFirst) {
    int n = inputMatrix.length;
    double[][] transformMatrix = createTransformMatrix(n);
    double[][] intermediate = new double[n][n];
    double[][] resultMatrix = new double[n][n];

    if (isRowFirst){
        //apply T to each row
        for (int i = 0; i < n; i++) {
            double[] row = inputMatrix[i];
            intermediate[i] = multiplyMatrixVector(transformMatrix, row);
        }

        //apply T to each col
        for (int j = 0; j < n; j++) {
            double[] column = getValuesOfColumn(intermediate, j);
            double[] dctColumn = multiplyMatrixVector(transformMatrix, column);
            for (int i = 0; i < n; i++) {
                resultMatrix[i][j] = dctColumn[i];
            }
        }
    }
    else {
        for (int j = 0; j < n; j++) {
            double[] column = getValuesOfColumn(inputMatrix, j);
            double[] dctColumn = multiplyMatrixVector(transformMatrix, column);
            for (int i = 0; i < n; i++) {
                intermediate[i][j] = dctColumn[i];
            }
        }
        for (int i = 0; i < n; i++) {
            resultMatrix[i] = multiplyMatrixVector(transformMatrix, intermediate[i]);
        }
    }

    return resultMatrix;
}
    private double[] getValuesOfColumn(double[][] matrix, int colIndex) {
        int n = matrix.length;
        double[] column = new double[n];
        for (int i = 0; i < n; i++) {
            column[i] = matrix[i][colIndex];
        }
        return column;
    }
    private double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
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
    private double[][] createTransformMatrix(int N) {
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
