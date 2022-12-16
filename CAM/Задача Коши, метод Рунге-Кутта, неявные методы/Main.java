import static java.lang.Math.abs;

public class Main {
    private static final double tau = 0.1;
    private static final double a = 1;
    private static final double b = 2;
    private static final int mTrapeze = 2;
    private static final int mRunge = 3;
    private static final double e = 1e-6;

    public static double getU(double t) {
        return Math.exp(t);
    }

    public static double getF(double t, double u) {
        return u * Math.log(u) / t;
    }

    public static double getF(double yj1, double yj, double tj1, double tau) {
        return yj1 - tau / 2 * (yj1 * Math.log(yj1) / tj1 + yj * Math.log(yj) / (tj1 - tau)) - yj;
    }

    public static double getDerivative(double yj1, double tj1, double tau) {
        return 1 - tau / 2 / tj1 * (1 + Math.log(yj1));
    }

    public static double[] solveImplicitTrapeze(double tau) {
        int N = (int) ((b - a) / tau);
        double[] y1 = new double[N + 1];
        y1[0] = getU(a);
        for (int i = 1; i <= N; i++) {
            y1[i] = solveNewton(y1[i - 1], a + i * tau, tau);
        }
        return y1;
    }

    private static double solveNewton(double yj, double t, double tau) {
        double xn = yj;
        double xn1 = xn - getF(xn, yj, t, tau) / getDerivative(xn, t, tau);
        while (Math.abs(xn1 - xn) > e) {
            xn = xn1;
            xn1 = xn - getF(xn, yj, t, tau) / getDerivative(xn, t, tau);
        }
        return xn1;
    }

    public static double getRunge(double[] un, double[] un1, int m) {
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

    private static double getAccuracy(double[] y, double tau) {
        double max = 0;
        for (int i = 0; i < y.length; i++) {
            double r = Math.abs(y[i] - getU(a + i * tau));
            if (r > max) {
                max = r;
            }
        }
        return max;
    }

    public static double[] solveExplicitRunge(double tau) {
        int N = (int) ((b - a) / tau);
        double[] y = new double[N + 1];
        y[0] = getU(a);
        for (int i = 1; i < N + 1; i++) {
            double t = a + (i - 1) * tau;
            double k1 = getF(t, y[i - 1]);
            double k2 = getF(t + tau / 2, y[i - 1] + tau * k1 / 2);
            double k3 = getF(t + tau, y[i - 1] - tau * k1 + 2 * tau * k2);
            y[i] = y[i - 1] + tau * (k1 + 4 * k2 + k3) / 6;
        }
        return y;
    }

    public static void main(String[] args) {
        System.out.println("Неявный метод трапеции:");
        double tau1 = tau;
        System.out.println("tau = " + tau1);
        double[] y1 = solveImplicitTrapeze(tau1);
        System.out.println("acc = " + getAccuracy(y1, tau1));

        double tau2 = tau / 2;
        System.out.println("tau = " + tau2);
        double[] y2 = solveImplicitTrapeze(tau2);
        System.out.println("acc = " + getAccuracy(y2, tau2));
        System.out.println("runge = " + getRunge(y1, y2, mTrapeze));

        System.out.println();
        System.out.println("Явный метод Рунге-Кутта:");
        System.out.println("tau = " + tau1);
        double[] y3 = solveExplicitRunge(tau1);
        System.out.println("acc = " + getAccuracy(y3, tau1));

        System.out.println("tau = " + tau2);
        double[] y4 = solveExplicitRunge(tau2);
        System.out.println("acc = " + getAccuracy(y4, tau2));
        System.out.println("runge = " + getRunge(y3, y4, mRunge));
    }
}
