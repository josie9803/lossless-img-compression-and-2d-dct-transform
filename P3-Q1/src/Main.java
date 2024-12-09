public class Main {
    public static void main(String[] args) {
        TextUI ui = new TextUI();
        DCTModel model = new DCTModel();

//        double[][] matrix = model.createTransformMatrix(8);
        double[] matrix = model.perform2DDCT();
        for (double val : matrix) {
            System.out.println(val);
        }

//        int N = ui.getMatrixSize();
//        double[][] matrix = ui.getMatrixValues(N);
//
//        double[][] dctRowFirst = model.applyDCT(matrix, N, true);
//        double[][] dctColFirst = model.applyDCT(matrix, N, false);
//
//        ui.printMatrix("DCT (Rows first, then columns):", dctRowFirst);
//        ui.printMatrix("DCT (Columns first, then rows):", dctColFirst);
//
//        boolean areEqual = model.compareMatrices(dctRowFirst, dctColFirst);
//        System.out.println("Are the matrices identical? " + areEqual);
    }
}