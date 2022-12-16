import java.util.Arrays;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Fredgolm {
    private static final double e = 1e-4;
    private static final int a = 0;
    private static final int b = 1;
    private static final double lambda = -1;
    private static final double mSimpson = 4;
    private static final double mTrapeze = 2;

    public static double getF(double x) {
        return PI * (x - 1 + cos(x) + x * sin(x));
    }

    public static double getK(double x, double s) {
        return x * x * cos(x * s);
    }

    public static double getU(double x) {
        return PI * x;
    }

    public static double[] successiveApproximationMethod(double h) {
        int N = (int) ((b - a) / h);
        double[] un = new double[N + 1];
        double[] un1 = new double[N + 1];
        do {
            un = Arrays.copyOf(un1, un1.length);
            un1 = new double[N + 1];
            for (int i = 0; i <= N; i++) {
                for (int j = 1; j < N; j++) {
                    un1[i] += getK(a + i * h, a + j * h) * un[j];
                }
                un1[i] = lambda * (h / 2) * (getK(a + i * h, a) * un[0] + getK(a + i * h, b) * un[N] + 2 * un1[i]) + getF(a + i * h);
            }
        } while (getAccuracy(un, un1) > e / 100);
        return un1;
    }

    public static double[] quadratureMethod(double h) {
        int N = (int) ((b - a) / h);
        double[] b = new double[N + 1];
        double[] A = new double[N + 1];

        A[0] = A[N] = h / 3;
        for (int i = 1; i < N; i += 2) {
            A[i] = h / 3 * 4;
        }
        for (int i = 2; i < N - 2; i += 2) {
            A[i] = h / 3 * 2;
        }
        double[][] matrix = new double[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                matrix[i][j] = -lambda * A[j] * getK(a + i * h, a + j * h);
            }
            matrix[i][i] += 1;
            b[i] = getF(a + i * h);
        }
        return new GaussMethod(matrix, b).solveMatrix();
    }

    public static double getAccuracy(double[] un, double[] un1) {
        double max = 0;
        for (int i = 0; i < un.length; i++) {
            if (abs(un[i] - un1[i]) > max) {
                max = abs(un[i] - un1[i]);
            }
        }
        return max;
    }

    public static double getAccuracy(double[] un, double h) {
        double max = 0;
        for (int i = 0; i < un.length; i++) {
            if (abs(un[i] - getU(a + i * h)) > max) {
                max = abs(un[i] - getU(a + i * h));
            }
        }
        return max;
    }

    public static double getRunge(double[] un, double[] un1, double m) {
        double max = 0;
        if (un.length == un1.length) {
            for (int i = 0; i < un.length; i++) {
                if (abs(un[i] - un1[i]) > max) {
                    max = abs(un[i] - un1[i]);
                }
            }
        } else {
            for (int i = 0; i < un.length; i++) {
                if (abs(un[i] - un1[2 * i]) > max) {
                    max = abs(un[i] - un1[2 * i]);
                }
            }
        }
        return max / (Math.pow(2, m) - 1);
    }

    public static void main(String[] args) {
        {
            System.out.println("Метод последовательных приближений");
            double h = (b - a) / 2.0;
            double[] un;
            double[] un1 = successiveApproximationMethod(h);
            do {
                un = Arrays.copyOf(un1, un1.length);
                h /= 2;
                un1 = successiveApproximationMethod(h);
                System.out.println(getRunge(un, un1, mTrapeze));
            } while (getRunge(un, un1, mTrapeze) > e);
            System.out.println("Погрешность: " + getAccuracy(un1, h));
        }

        System.out.println();
        {
            System.out.println("Метод квадратур");
            double h = (b - a) / 2.0;
            double[] un;
            double[] un1 = quadratureMethod(h);
            do {
                un = Arrays.copyOf(un1, un1.length);
                h /= 2;
                un1 = quadratureMethod(h);
                System.out.println(getRunge(un, un1, mSimpson));
            } while (getRunge(un, un1, mSimpson) > e);
            System.out.println("Погрешность: " + getAccuracy(un1, h));
        }
    }
}