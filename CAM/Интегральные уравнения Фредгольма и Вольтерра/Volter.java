import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sin;

public class Volter {
    private static final double e = 5e-4;
    private static final int a = 0;
    private static final int b = 1;
    private static final double lambda = 1;
    private static final double mTrapeze = 2;

    public static double getF(double x) {
        return exp(x) * sin(x);
    }

    public static double getK(double x, double s) {
        return (2.0 + cos(x)) / (2.0 + cos(s));
    }

    public static double getU(double x) {
        return exp(x) * sin(x) + (2.0 + cos(x)) * (exp(x) * log(3.0 / (2.0 + cos(x))));
    }

    public static double[] successiveApproximationMethod(double h) {
        int N = (int) ((b - a) / h);
        double[] yn = new double[N + 1];
        double[] yn1 = new double[N + 1];
        for (int i = 0; i <= N; i++) {
            yn[i] = getF(a + i * h);
        }
        do {
            yn = Arrays.copyOf(yn1, yn1.length);
            yn1 = new double[N + 1];
            yn1[0] = getF(a);
            yn1[1] = lambda * h / 2 * (getK(a + h, a) * yn[0] + getK(a + h, a + h) * yn[1]) + getF(a + h);
            for (int i = 2; i <= N; i++) {
                for (int j = 1; j < i; j++) {
                    yn1[i] += getK(a + i * h, a + j * h) * yn[j];
                }
                yn1[i] = lambda * (h / 2 * (getK(a + i * h, a) * yn[0] + getK(a + i * h, a + i * h) * yn[i]) + h * yn1[i]) + getF(a + i * h);
            }
        } while (getAccuracy(yn, yn1) > e / 100);
        return yn1;
    }

    public static double[] quadratureMethod(double h) {
        int N = (int) ((b - a) / h);
        double[] un = new double[N + 1];
        double[] un1 = new double[N + 1];
        do {
            un = Arrays.copyOf(un1, un1.length);
            un1 = new double[N + 1];
            un1[0] = getF(a);
            un1[1] = (getF(a) + lambda * h / 2 * getK(a + h, a) * un1[0]) / (1 - lambda * h * getK(a + h, a + h));
            for(int i = 2; i <= N; i++) {
                for(int j = 1; j < i; j++) {
                    un1[i] += h * getK(a + i * h, a + j * h) * un1[j];
                }
                un1[i] = (getF(a + i * h) + lambda * un1[i] + lambda * h / 2 * getK(a + i * h, a) * un1[0]) / (1 - lambda * h / 2 * getK(a + i * h, a + i * h));
            }
        } while (getAccuracy(un, un1) > e);
        return un1;
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
                System.out.println(getRunge(un, un1, mTrapeze));
            } while (getRunge(un, un1, mTrapeze) > e);
            System.out.println("Погрешность: " + getAccuracy(un1, h));
        }
    }
}