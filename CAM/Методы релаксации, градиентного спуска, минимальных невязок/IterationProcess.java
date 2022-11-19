public class IterationProcess {
    private static final int SIZE = 10;
    private static final int RANGE = 100;
    private static final int K_MAX = 5000;
    private static final double EPS = 0.0000001;
    private static final double[] omega = {0.2, 0.5, 0.8, 1, 1.3, 1.5, 1.8};
    private static int amountOfOperations;

    public static double[] multiply(double[][] matrix, double[] x) {
        double[] f = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                f[i] += matrix[i][j] * x[j];
            }
        }
        return f;
    }

    public static double multiply(double[] vector, double[] x) {
        double f = 0;
        for (int i = 0; i < vector.length; i++) {
            f += vector[i] * x[i];
        }
        return f;
    }

    public static double[] multiply(double[] vector, double d) {
        double[] f = vector.clone();
        for (int i = 0; i < vector.length; i++) {
            f[i] *= d;
        }
        return f;
    }

    public static double[] subtract(double[] array1, double[] array2) {
        double[] answer = array1.clone();
        for (int i = 0; i < array1.length; i++) {
            answer[i] -= array2[i];
        }
        return answer;
    }

    public static double[] getAccurateX() {
        double[] array = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    public static double[][] generateMatrix() {
        double[][] matrix = new double[SIZE][SIZE];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                matrix[i][j] = (int) (Math.random() * 2 * RANGE - RANGE);
                matrix[j][i] = matrix[i][j];
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                sum += Math.abs(matrix[i][j]);
            }
            matrix[i][i] = (int) (sum + Math.random() * 63 + 7);
        }
        return matrix;
    }

    public static double getAccuracy(double[] accurateX, double[] calculatedX) {
        double[] accuracy = accurateX.clone();
        for (int i = 0; i < accuracy.length; i++) {
            accuracy[i] -= calculatedX[i];
        }
        return Math.abs(getMax(accuracy) / getMax(accurateX));
    }

    public static double getMax(final double[] array) {
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Math.abs(array[i]) > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static void printMatrix(double[] array) {
        for (double d : array) {
            System.out.println(d);
        }
    }

    public static void printMatrix(final double[][] matrix, double[] f) {
        for (int i = 0; i < matrix.length; i++) {
            for (double v : matrix[i]) {
                System.out.printf("%6.2f", v);
            }
            System.out.println(" | " + String.format("%4.0f", f[i]));
        }
    }

    public static double[] solveMinimalResidualMethod(double[][] matrix, double[] f) {
        amountOfOperations = 0;
        double[] x = f.clone();
        double[] residual = getResidual(matrix, x, f);
        while (amountOfOperations < K_MAX && Math.abs(getMax(residual)) > EPS) {
            double tau = getTauForResidual(matrix, residual);
            x = subtract(x, multiply(residual, tau));
            residual = getResidual(matrix, x, f);
            amountOfOperations++;
        }
        if (amountOfOperations >= K_MAX) {
            System.out.println("Amount of operations is exceeded.");
        }
        return x;
    }

    public static double[] solveGradientMethod(double[][] matrix, double[] f) {
        amountOfOperations = 0;
        double[] x = f.clone();
        double[] residual = getResidual(matrix, x, f);
        while (amountOfOperations < K_MAX && Math.abs(getMax(residual)) > EPS) {
            double tau = getTauForGradient(matrix, residual);
            x = subtract(x, multiply(residual, tau));
            residual = getResidual(matrix, x, f);
            amountOfOperations++;
        }
        if (amountOfOperations >= K_MAX) {
            System.out.println("Amount of operations is exceeded.");
        }
        return x;
    }

    public static double[] solveRelaxationMethod(double[][] matrix, double[] f, double omega) {
        amountOfOperations = 0;
        double[] x = f.clone();
        do {
            for (int i = 0; i < x.length; i++) {
                double sum = f[i];
                for (int k = 0; k < x.length; k++) {
                    sum -= matrix[i][k] * x[k];
                }
                sum += matrix[i][i] * x[i];
                x[i] = (1 - omega) * x[i] + omega / matrix[i][i] * (sum);
            }
            amountOfOperations++;
        } while (amountOfOperations < K_MAX && Math.abs(getMax(getResidual(matrix, x, f))) > EPS);
        if (amountOfOperations >= K_MAX) {
            System.out.println("Amount of operations is exceeded.");
        }
        return x;
    }

    public static double[] getResidual(double[][] matrix, double[] x, double[] f) {
        double[] Ax = multiply(matrix, x);
        for (int i = 0; i < Ax.length; i++) {
            Ax[i] -= f[i];
        }
        return Ax;
    }

    public static double getTauForResidual(double[][] matrix, double[] residual) {
        double[] Ar = multiply(matrix, residual);
        double Arr = multiply(Ar, residual);
        double ArAr = multiply(Ar, Ar);
        return Arr / ArAr;
    }

    public static double getTauForGradient(double[][] matrix, double[] residual) {
        double[] Ar = multiply(matrix, residual);
        double Arr = multiply(Ar, residual);
        double rr = multiply(residual, residual);
        return rr / Arr;
    }

    public static void main(String[] args) {
        double[][] matrix = generateMatrix();
        double[] accurateX = getAccurateX();
        double[] f = multiply(matrix, accurateX);
        printMatrix(matrix, f);
        System.out.println("Accurate x:");
        printMatrix(accurateX);
        System.out.println("x(0):");
        printMatrix(f);

        double[] calculatedX = solveMinimalResidualMethod(matrix, f);
        printMatrix(calculatedX);
        System.out.println("Amount of operations: " + amountOfOperations);
        double accuracy = getAccuracy(accurateX, calculatedX);
        System.out.println("Accuracy: " + accuracy);
        System.out.println("||Ax-f||: " + getMax(getResidual(matrix, calculatedX, f)));
        System.out.println("-----------------------------");

        double[] calculatedX2 = solveGradientMethod(matrix, f);
        printMatrix(calculatedX2);
        System.out.println("Amount of operations: " + amountOfOperations);
        double accuracy2 = getAccuracy(accurateX, calculatedX2);
        System.out.println("Accuracy: " + accuracy2);
        System.out.println("||Ax-f||: " + getMax(getResidual(matrix, calculatedX2, f)));
        System.out.println("-----------------------------");

        for (double w : omega) {
            System.out.println("omega = " + w);
            double[] calculatedX3 = solveRelaxationMethod(matrix, f, w);
            printMatrix(calculatedX3);
            System.out.println("Amount of operations: " + amountOfOperations);
            double accuracy3 = getAccuracy(accurateX, calculatedX3);
            System.out.println("Accuracy: " + accuracy3);
            System.out.println("||Ax-f||: " + getMax(getResidual(matrix, calculatedX3, f)));
        }
    }
}
