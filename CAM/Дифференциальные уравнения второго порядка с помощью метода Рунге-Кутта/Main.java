import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.exp;

public class Main {
    private static final double h = 0.2;
    private static final double a = 1.0;
    private static final double b = 2.0;
    private static final double m = 3;

    public static double getU(double t) {
        return t * exp(t);
    }

    public static double getU10() {
        return exp(1);
    }

    public static double getU20() {
        return 2 * exp(1);
    }

    public static double getF1(double t, double y1, double y2) {
        return y2;
    }

    public static double getF2(double t, double y1, double y2) {
        return (1 - 2.0 / t) * y2 + (3 * t + 2) / t / t * y1;
    }

    public static double[] solveRunge(double h) {
        double[][] K = new double[3][2];
        int N = (int) ((b - a) / h);
        double[] y1 = new double[N + 1];
        double[] y2 = new double[N + 1];
        y1[0] = getU10();
        y2[0] = getU20();
        for (int j = 0; j < N; j++) {
            double t = a + j * h;
            K[0][0] = getF1(t, y1[j], y2[j]);
            K[0][1] = getF2(t, y1[j], y2[j]);
            K[1][0] = getF1(t + h / 3, y1[j] + h / 3 * K[0][0], y2[j] + h / 3 * K[0][1]);
            K[1][1] = getF2(t + h / 3, y1[j] + h / 3 * K[0][0], y2[j] + h / 3 * K[0][1]);
            K[2][0] = getF1(t + h, y1[j] - h * K[0][0] + 2 * h * K[1][0], y2[j] - h * K[0][1] + 2 * h * K[1][1]);
            K[2][1] = getF2(t + h, y1[j] - h * K[0][0] + 2 * h * K[1][0], y2[j] - h * K[0][1] + 2 * h * K[1][1]);
            y1[j + 1] = y1[j] + h / 4 * (3 * K[1][0] + K[2][0]);
            y2[j + 1] = y2[j] + h / 4 * (3 * K[1][1] + K[2][1]);
        }
        return y1;
    }

    public static double getRunge(double[] un, double[] un1) {
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

    private static double getAccuracy(double[] y, double h) {
        double max = 0;
        for (int i = 0; i < y.length; i++) {
            double r = Math.abs(y[i] - getU(a + i * h));
            if (r > max) {
                max = r;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println("h = " + h);
        double[] result1 = solveRunge(h);
        System.out.println(Arrays.toString(result1));
        System.out.println(getAccuracy(result1, h));

        System.out.println("h = " + h / 2);
        double[] result2 = solveRunge(h / 2);
        System.out.println(Arrays.toString(result2));
        System.out.println(getAccuracy(result2, h / 2));

        System.out.println("Accuracy by Runge = " + getRunge(result1, result2));
    }
}
