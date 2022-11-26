public class Main {
    private static final double e = 1e-6;
    private static final double[] x0 = new double[]{-1, 1};
    private static final double[] x1 = new double[]{-0.99, 2.5};
    private static int counter;

    public static double function1(double[] x) {
        return Math.exp(x[0] - 3 * x[1]) + x[0] * x[0] - x[1] * x[1] + 3;
    }

    public static double function2(double[] x) {
        return x[0] * x[0] + 2 * x[1] * x[1] - 9;
    }

    public static double function1(double[] x1, int pos, double[] x2) {
        if(pos == 2) {
            return Math.exp(x2[0] - 3 * x1[1]) + x2[0] * x2[0] - x1[1] * x1[1] + 3;
        } else {
            return Math.exp(x1[0] - 3 * x2[1]) + x1[0] * Main.x1[0] - x2[1] * x2[1] + 3;
        }
    }

    public static double function2(double[] x1, int pos, double[] x2) {
        if(pos == 2) {
            return x2[0] * x2[0] + 2 * x1[1] * x1[1] - 9;
        } else {
            return x1[0] * x1[0] + 2 * x2[1] * x2[1] - 9;
        }
    }

    public static double[][] derivativeForNewton(double[] x) {
        return new double[][]{
                {Math.exp(x[0] - 3 * x[1]) + 2 * x[0], -2 * x[1]},
                {2 * x[0], 4 * x[1]}};
    }

    public static double[][] derivativeForSecant(double[] x1, double[] x2) {
        return new double[][]{
                {(function1(x2) - function1(x1, 1, x2)) / (x2[0] - x1[0]), (function1(x2) - function1(x1, 2, x2)) / (x2[1] - x1[1])},
                {(function2(x2) - function2(x1, 1, x2)) / (x2[0] - x1[0]), (function2(x2) - function2(x1, 2, x2)) / (x2[1] - x1[1])}};
    }

    public static void printMatrix(final double[] f) {
        for (double v : f) {
            System.out.println(v);
        }
    }

    public static double[] newtonMethod() {
        counter = 0;
        double[] x1;
        double[] x2 = x0.clone();
        do {
            x1 = x2;
            double[] dx = new GaussMethod(derivativeForNewton(x2), new double[]{-function1(x2), -function2(x2)}).solveMatrix();
            x2 = sum(x1, dx);
            counter++;
            System.out.println("iteration " + counter);
            printMatrix(x2);
            System.out.println(getNorm(x1, x2));
        } while (getNorm(x1, x2) > e);
        return x2;
    }

    public static double[] secantMethod() {
        double[] xk1 = x0.clone();
        double[] xk2 = x1.clone();
        counter = 0;
        while (getNorm(xk1, xk2) > e) {
            double[] dk = new GaussMethod(derivativeForSecant(xk1, xk2), new double[]{-function1(xk2), -function2(xk2)}).solveMatrix();
            xk1 = xk2.clone();
            xk2 = sum(xk1, dk);
            counter++;
            System.out.println("secant " + counter);
            printMatrix(xk2);
            System.out.println(getNorm(xk1, xk2));
        }
        return xk2;
    }

    public static double getNorm(double[] x1, double[] x2) {
        return Math.max(Math.abs(x1[0] - x2[0]), Math.abs(x1[1] - x2[1]));
    }

    public static double getNorm(double[] x1) {
        return Math.max(Math.abs(x1[0]), Math.abs(x1[1]));
    }

    public static double[] sum(double[] x1, double[] x2) {
        return new double[]{x1[0] + x2[0], x1[1] + x2[1]};
    }

    public static void main(String[] args) {
        double[] newton = newtonMethod();
        System.out.println("||f|| = " + getNorm(new double[]{function1(newton), function2(newton)}));
        double[] secant = secantMethod();
        System.out.println("||f|| = " + getNorm(new double[]{function1(secant), function2(secant)}));
    }
}
