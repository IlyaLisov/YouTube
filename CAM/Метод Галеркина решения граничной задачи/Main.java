public class Main {

    private static final double[] interval = {0.0, 1.0};
    private static final double kappa0 = 1.0 / 3;
    private static final double kappa1 = 0;
    private static final double g0 = 2.0;
    private static final double g1 = 1.0;

    public static double getQ(double x) {
        return 3.0;
    }

    public static double getR(double x) {
        return x + 2;
    }

    public static double getU(double x) {
        return x * x * x - 2.0 * x;
    }

    public static double getf(double x) {
        return -(6 * x * x + 10 * x - 4);
    }

    public static double getK(double x) {
        return 1;
    }

    public static double a(double x, double h) {
        return getK(x - h / 2) - h * h / 6 * getQ(x - h / 2);
    }

    public static double bplus(double x, double h) {
        return getR(x + h / 2) / 2;
    }

    public static double bminus(double x, double h) {
        return getR(x - h / 2) / 2;
    }

    public static double d(double x, double h) {
        if (x == interval[0]) {
            return getQ(interval[0] + h / 2);
        } else if (x == interval[1]) {
            return getQ(interval[1] - h / 2);
        }
        return (getQ(x - h / 2) + getQ(x + h / 2)) / 2;
    }

    public static double fi(double x, double h) {
        if (x == interval[0]) {
            return getf(interval[0] + h / 2);
        } else if (x == interval[1]) {
            return getf(interval[1] - h / 2);
        }
        return (getf(x - h / 2) + getf(x + h / 2)) / 2;
    }

    public static double getC(double x, double h) {
        if (x == interval[0]) {
            return (a(interval[0] + h, h) + bplus(interval[0], h) * h) / h + kappa0 + h / 2 * d(interval[0], h);
        } else if (x == interval[1]) {
            return (a(interval[1], h) - bplus(interval[1], h) * h) / h + kappa1 + h / 2 * d(interval[1], h);
        } else {
            return (a(x + h, h) + a(x, h)) / h / h + bplus(x, h) / h - bminus(x, h) / h + d(x, h);
        }
    }

    public static double getB(double x, double h) {
        if (x == interval[0]) {
            return (a(interval[0] + h, h) + bplus(interval[0], h) * h) / h;
        } else {
            return a(x + h, h) / h / h + bplus(x, h) / h;
        }
    }

    public static double getA(double x, double h) {
        if (x == interval[1]) {
            return (a(interval[1], h) - bplus(interval[1], h) * h) / h;
        } else {
            return a(x, h) / h / h - bminus(x, h) / h;
        }
    }

    public static double getF(double x, double h) {
        if (x == interval[0]) {
            return g0 + h / 2 * fi(interval[0], h);
        } else if (x == interval[1]) {
            return g1 + h / 2 * fi(interval[1], h);
        } else {
            return fi(x, h);
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

    public static void main(String[] args) {
        double[] y1;
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
        y1 = solve(a, b, c, f, h);
        System.out.println("h = " + h);
        System.out.println("accuracy = " + getAccuracy(y1, h));
    }

}
