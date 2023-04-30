public class Main {

    public static double getU(double x, double t) {
        return Math.sin(x * t);
    }

    public static double getMu0(double t) {
        return getU(0, t);
    }

    public static double getMu1(double t) {
        return getU(1, t);
    }

    public static double getU0(double x) {
        return getU(x, 0);
    }

    public static double getF(double x, double t) {
        return x * Math.cos(x * t) + t * t * Math.sin(x * t);
    }

    public static double[][] solve1(double h, double tau) {
        int N1 = (int) (1 / h);
        int N2 = (int) (1 / tau);
        double[][] y = new double[N1 + 1][N2 + 1];
        for (int i = 0; i <= N1; i++) {
            y[i][0] = getU0(i * h);
        }

        for (int j = 0; j <= N2 - 1; j++) {
            y[0][j + 1] = getMu0((j + 1) * tau);
            for (int i = 1; i < N1; i++) {
                y[i][j + 1] = tau / (h * h) * (y[i + 1][j] - 2 * y[i][j] + y[i - 1][j])
                        + tau * getF(i * h, j * tau)
                        + y[i][j];
            }
            y[N1][j + 1] = getMu1((j + 1) * tau);
        }

        return y;
    }

    public static double[][] solve2(double h, double tau, double sigma) {
        int N1 = (int) (1 / h);
        int N2 = (int) (1 / tau);
        double[][] y = new double[N2 + 1][N1 + 1];
        for (int i = 0; i <= N1; i++) {
            y[0][i] = getU0(i * h);
        }
        double[] a = new double[N1 + 1];
        double[] b = new double[N1 + 1];
        double[] c = new double[N1 + 1];
        double[] f = new double[N1 + 1];
        for (int j = 0; j < N2; j++) {
            c[0] = 1;
            f[0] = getMu0(tau * (j + 1));
            f[N1] = getMu1(tau * (j + 1));
            b[0] = 0;
            a[N1] = 0;
            c[N1] = 1;
            for (int i = 1; i < N1; i++) {
                a[i] = sigma / (h * h);
                c[i] = (1 / tau + 2 * sigma / (h * h));
                b[i] = sigma / (h * h);
                f[i] = y[j][i] / tau + (1 - sigma) * 1 / (h * h) * (y[j][i + 1] - 2 * y[j][i] + y[j][i - 1]) + getF(i * h, j * tau);
            }
            y[j + 1] = solveTridiagonal(a, b, c, f, tau);
        }

        return y;
    }

    public static double[][] solve3(double h, double tau) {
        int N1 = (int) (1 / h);
        int N2 = (int) (1 / tau);
        double[][] y = new double[N2 + 1][N1 + 1];
        for (int i = 0; i <= N1; i++) {
            y[0][i] = getU0(i * h);
        }
        double[] a = new double[N1 + 1];
        double[] b = new double[N1 + 1];
        double[] c = new double[N1 + 1];
        double[] f = new double[N1 + 1];
        for (int j = 0; j < N2; j++) {
            c[0] = 1;
            f[0] = getMu0(tau * (j + 1));
            f[N1] = getMu1(tau * (j + 1));
            b[0] = 0;
            a[N1] = 0;
            c[N1] = 1;
            for (int i = 1; i < N1; i++) {
                a[i] = 1 / (h * h * 2);
                c[i] = 1 / tau + 1 / (h * h);
                b[i] = 1 / (2 * h * h);
                f[i] = y[j][i] / tau + getF(i * h, (j + 1.0 / 2) * tau) + 1 / (2 * h * h) * (y[j][i + 1] - 2 * y[j][i] + y[j][i - 1]);
            }
            y[j + 1] = solveTridiagonal(a, b, c, f, tau);
        }

        return y;
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] doubles : matrix) {
            for (double v : doubles) {
                System.out.printf("%18.4f", v);
            }
            System.out.print(System.lineSeparator());
        }
    }

    public static double getAccuracy(double[][] matrix, double h, double tau) {
        double max = 0;
        int N1 = (int) (1 / h + 1);
        int N2 = (int) (1 / tau + 1);
        for (int i = 0; i < N1; i++) {
            for (int j = 0; j < N2; j++) {
                double current = Math.abs(getU(i * h, j * tau) - matrix[i][j]);
                if (current > max) {
                    max = current;
                }
            }
        }
        return max;
    }

    public static double getAccuracy2(double[][] matrix, double h, double tau) {
        double max = 0;
        int N1 = (int) (1 / h + 1);
        int N2 = (int) (1 / tau + 1);
        for (int i = 0; i < N1; i++) {
            for (int j = 0; j < N2; j++) {
                double current = Math.abs(getU(i * h, j * tau) - matrix[j][i]);
                if (current > max) {
                    max = current;
                }
            }
        }
        return max;
    }

    public static double[] solveTridiagonal(double[] a, double[] b, double[] c, double[] f, double h) {
        int SIZE = (int) (1 / h) + 1;
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

    public static void main(String[] args) {
        {
            double h = 0.1;
            double tau = 0.1;
            double[][] result1 = solve1(h, tau);
//            printMatrix(result1);
            double accuracy = getAccuracy(result1, h, tau);
            System.out.println(accuracy);
        }
        {
            double h = 0.1;
            double tau = h * h / 2;
            double[][] result1 = solve1(h, tau);
//            printMatrix(result1);
            double accuracy = getAccuracy(result1, h, tau);
            System.out.println(accuracy);
        }
        {
            double h = 0.1;
            double tau = 0.1;
            double[][] result2 = solve2(h, tau, 1);
//            printMatrix(result2);
            double accuracy = getAccuracy2(result2, h, tau);
            System.out.println(accuracy);
        }
        {
            double h = 0.1;
            double tau = 0.1;
            double[][] result3 = solve3(h, tau);
//            printMatrix(result3);
            double accuracy = getAccuracy2(result3, h, tau);
            System.out.println(accuracy);
        }
    }

}
