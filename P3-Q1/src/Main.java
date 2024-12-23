public class Main {
    public static void main(String[] args) {
        TextUI ui = new TextUI();
        DCTModel model = new DCTModel();

//        double[][] matrix = {
//                {89, 78, 76, 75, 70, 82, 81, 82},
//                {122, 95, 86, 80, 80, 76, 74, 81},
//                {184, 153, 126, 106, 85, 76, 71, 75},
//                {221, 205, 180, 146, 97, 71, 68, 67},
//                {225, 222, 217, 194, 144, 95, 78, 82},
//                {228, 225, 227, 220, 193, 146, 110, 108},
//                {223, 224, 225, 224, 220, 197, 156, 120},
//                {217, 219, 219, 224, 230, 220, 197, 151}
//        };
        int N = ui.getMatrixSize();
        double[][] matrix = ui.getMatrixValues(N);

        double[][] dctRowFirst = model.perform2DDCT(matrix, true);
        double[][] dctColFirst = model.perform2DDCT(matrix, false);
        ui.printMatrix("DCT (Rows first, then columns):", dctRowFirst);
        ui.printMatrix("DCT (Columns first, then rows):", dctColFirst);

        boolean areEqual = model.compareMatrices(dctRowFirst, dctColFirst);
        System.out.println("Are the matrices identical? " + areEqual);
    }
}