public class Main {

    private static final double[] interval = {0.0, 1.0};
    private static final double sigma0 = 1.0 / 3;
    private static final double sigma1 = 0;
    private static final double mu0 = 2.0;
    private static final double mu1 = 1.0;

    public static double getQ(double x) {
        return 3.0;
    }

    public static double getG(double x) {
        return x + 2;
    }

    public static double getSigma0(double h) {
        return h / 2 * getQ(interval[0]) + sigma0 * (1 - h / 2 * getG(interval[0]));
    }

    public static double getSigma1(double h) {
        return h / 2 * getQ(interval[1]) + (1 + h / 2 * getG(interval[1])) * sigma1;
    }

    public static double getMu0(double h) {
        return mu0 * (1 - h / 2 * getG(interval[0])) + h / 2 * getf(interval[0]);
    }

    public static double getMu1(double h) {
        return mu1 * (1 + h / 2 * getG(interval[1])) + h / 2 * getf(interval[1]);
    }

    public static double getU(double x) {
        return x * x * x - 2.0 * x;
    }

    public static double getf(double x) {
        return -(6 * x * x + 10 * x - 4);
    }

    public static double getC(double x, double h) {
        if (x == interval[0]) {
            return -1 / h - getSigma0(h);
        } else if (x == interval[1]) {
            return 1 / h;
        } else {
            return getQ(x) + 2 / h / h;
        }
    }

    public static double getB(double x, double h) {
        if (x == interval[0]) {
            return -1 / h;
        } else {
            return 1 / h / h + getG(x) / 2 / h;
        }
    }

    public static double getA(double x, double h) {
        if (x == interval[1]) {
            return 1 / h - getSigma1(h);
        } else {
            return 1 / h / h - getG(x) / 2 / h;
        }
    }

    public static double getF(double x, double h) {
        if (x == interval[0]) {
            return -getMu0(h);
        } else if (x == interval[1]) {
            return getMu1(h);
        } else {
            return getf(x);
        }
    }

    public static double[] solve(double[] a, double[] b, double[] c, double[] f, double h) {
        int SIZE = (int) ((interval[1] - interval[0]) / h) + 1;
        double[] y = new double[SIZE];
        double[] alpha = new double[SIZE + 1];
        double[] beta = new double[SIZE + 1];

        alpha[1] = b[0] / c[0];
        beta[1] = f[0] / c[0];
        for (int i = 1; i < SIZE - 1; i++) {
            double divisor = c[i] - a[i] * alpha[i];
            if (divisor == 0) {
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

    public static double getAccuracy(double[] array, double h) {
        double max = 0;
        for (int i = 0; i < array.length; i++) {
            double current = Math.abs(getU(interval[0] + i * h) - array[i]);
            if (current > max) {
                max = current;
            }
        }
        return max;
    }

    public static double getRunge(double[] y1, double[] y2) {
        double max = 0;
        for (int i = 0; i < y1.length; i++) {
            double current = Math.abs(y1[i] - y2[2 * i]);
            if (current > max) {
                max = current;
            }
        }
        return max / 3;
    }

    public static void main(String[] args) {
        double[] y1;
        double[] y2;
        {
            double h = 0.02;
            int N = (int) ((interval[1] - interval[0]) / h) + 1;
            double[] a = new double[N];
            double[] b = new double[N];
            double[] c = new double[N];
            double[] f = new double[N];
            for (int i = 0; i < N; i++) {
                a[i] = getA(interval[0] + i * h, h);
                b[i] = getB(interval[0] + i * h, h);
                c[i] = getC(interval[0] + i * h, h);
                f[i] = getF(interval[0] + i * h, h);
            }
            y1 = solve(a, b, c, f, h);
            System.out.println("h = " + h);
            System.out.println("accuracy = " + getAccuracy(y1, h));
        }

        {
            double h = 0.01;
            int N = (int) ((interval[1] - interval[0]) / h) + 1;
            double[] a = new double[N];
            double[] b = new double[N];
            double[] c = new double[N];
            double[] f = new double[N];
            for (int i = 0; i < N; i++) {
                a[i] = getA(interval[0] + i * h, h);
                b[i] = getB(interval[0] + i * h, h);
                c[i] = getC(interval[0] + i * h, h);
                f[i] = getF(interval[0] + i * h, h);
            }
            a[0] = b[N - 1] = 0;
            y2 = solve(a, b, c, f, h);
            System.out.println("h = " + h);
            System.out.println("accuracy = " + getAccuracy(y2, h));
        }

        System.out.println("Runge = " + getRunge(y1, y2));
    }

}
