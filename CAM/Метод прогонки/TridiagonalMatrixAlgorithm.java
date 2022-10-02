import java.util.Arrays;

public class TridiagonalMatrixAlgorithm {
    private static final int SIZE = 10;
    private static final int[] RANGE = {-100, 100};
    private static final int index = 7;

    public static double[] getRandomArray() {
        double[] a = new double[SIZE];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * (RANGE[1] - RANGE[0]) - RANGE[1]);
        }
        return a;
    }

    public static double[] getC(double[] a, double[] b) {
        double[] c = new double[SIZE];
        c[0] = (Math.abs(b[0]) + index) + (int) (Math.random() * index);
        c[c.length - 1] = (Math.abs(a[0]) + index) + (int) (Math.random() * index);
        for (int i = 1; i < c.length - 1; i++) {
            c[i] = (Math.abs(a[i]) + Math.abs(b[i]) + index) + (int) (Math.random() * index);
        }
        return c;
    }

    public static double[] getY() {
        double[] y = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            y[i] = i + 1;
        }
        return y;
    }

    public static double[] getF(double[] a, double[] b, double[] c, double[] y) {
        double[] f = new double[SIZE];
        f[0] = c[0] * y[0] - b[0] * y[1];
        f[SIZE - 1] = -a[SIZE - 1] * y[SIZE - 2] + c[SIZE - 1] * y[SIZE - 1];
        for (int i = 1; i < SIZE - 1; i++) {
            f[i] = -a[i] * y[i - 1] + c[i] * y[i] - b[i] * y[i + 1];
        }
        return f;
    }

    public static double[] solve(double[] a, double[] b, double[] c, double[] f) {
        double[] y = new double[SIZE];
        double[] alpha = new double[SIZE + 1];
        double[] beta = new double[SIZE + 1];

        alpha[1] = b[0] / c[0];
        beta[1] = f[0] / c[0];
        for (int i = 1; i < SIZE - 1; i++) {
            double divisor = c[i] - a[i] * alpha[i];
            if (divisor == 0) { //epsilon
                System.out.println("Метод не может быть применим - деление на 0");
                return null;
            } else {
                alpha[i + 1] = b[i] / divisor;
                beta[i + 1] = (f[i] + a[i] * beta[i]) / divisor;
            }
        }
        beta[SIZE] = (f[SIZE - 1] + a[SIZE - 1] * beta[SIZE - 1]) / (c[SIZE - 1] - a[SIZE - 1] * alpha[SIZE - 1]);

        y[SIZE - 1] = beta[SIZE];
        for (int i = SIZE - 2; i >= 0; i--) {
            y[i] = alpha[i + 1] * y[i + 1] + beta[i + 1];
        }

        return y;
    }

    public static double getAccuracy(final double[] accurateX, final double[] calculatedX) {
        double[] accuracy = Arrays.copyOf(accurateX, SIZE);
        for (int i = 0; i < SIZE; i++) {
            accuracy[i] -= calculatedX[i];
        }
        return Math.abs(getMax(accuracy) / getMax(accurateX));
    }

    public static double getMax(final double[] array) {
        double max = array[0];
        for (int i = 1; i < SIZE; i++) {
            if (Math.abs(array[i]) > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        double[] a = getRandomArray();
        a[0] = 0;
        double[] b = getRandomArray();
        b[SIZE - 1] = 0;
        double[] c = getC(a, b);
        double[] accurateY = getY();
        double[] f = getF(a, b, c, accurateY);
        double[] calculatedY = solve(a, b, c, f);

        System.out.println("VECTOR a: " + Arrays.toString(a));
        System.out.println("VECTOR c: " + Arrays.toString(c));
        System.out.println("VECTOR b: " + Arrays.toString(b));
        System.out.println("VECTOR accurate y: " + Arrays.toString(accurateY));
        System.out.println("VECTOR f: " + Arrays.toString(f));
        System.out.println("Calculated y: " + Arrays.toString(calculatedY));
        System.out.println("Accuracy: " + getAccuracy(accurateY, calculatedY));
    }
}