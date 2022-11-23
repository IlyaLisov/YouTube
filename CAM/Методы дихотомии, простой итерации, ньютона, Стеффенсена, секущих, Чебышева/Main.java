import java.util.Arrays;

public class Main {
    //x*exp(x) + x^2 - 1 = 0, x < 0
    //x* in [-2;0]
    //x = x + (1-x*exp(x)-x^2)/(exp(x)+x)
    //f`(x) = (-exp(2x) - 2xexp(x)-x^2-exp(x)-1)/((exp(x)+x)^2
    private static final double ACCURACY_IN_DICHOTOMY = 0.1;
    private static final double ACCURACY = 10e-7;
    private static final double a = -2;
    private static final double b = 0;

    public static double function(double x) {
        return x * Math.exp(x) + x * x - 1;
    }

    public static double functionFi(double x) {
        return x + (1 - x * Math.exp(x) - x * x) / (Math.exp(x) + x);
    }

    public static double derivative(double x) {
        return x * Math.exp(x) + Math.exp(x) + 2 * x;
    }

    public static double derivativeF(double x) {
        return (-Math.exp(2 * x) - 2 * x * Math.exp(x) - x * x - Math.exp(x) - 1) / (Math.exp(x) + x) / (Math.exp(x) + x);
    }

    public static double secondDerivative(double x) {
        return x * Math.exp(x) + 2 * Math.exp(x) + 2;
    }

    public static double[] dichotomyMethod(double a, double b) {
        int counter = 0;
        System.out.println("k" + "\t\t|\t\t" + "ak" + "\t|\t" + "bk" + "\t|\t" + "f(ak)" + "\t|\t" + "f(bk)" + "\t|\t" + "(ak + bk) / 2" + "\t|\t" + "bk - ak" + "\t|");
        double mid = (a + b) / 2.0;
        while (Math.abs(b - a) >= ACCURACY_IN_DICHOTOMY) {
            System.out.println(counter + "\t\t|\t" + a + "\t|\t" + b + "\t|\t" + function(a) + "\t|\t" + function(b) + "\t|\t" + mid + "\t|\t" + (b - a));
            if (function(mid) * function(a) < 0) {
                b = mid;
            } else if (function(mid) * function(b) < 0) {
                a = mid;
            }
            mid = (a + b) / 2.0;
            counter++;
        }
        return new double[]{a, b};
    }

    public static double simpleIterationMethod(double[] interval) {
        double x = (interval[1] + interval[0]) / 2;
        double delta = Math.abs(interval[1] - interval[0]);
        double x1 = x;
        final double C = -2 / (derivativeF(interval[0]) + derivativeF(interval[1]));
        final double q = Math.max(Math.abs(1 + C * derivative(interval[0])), Math.abs(1 + C * derivative(interval[1])));
        int counter = 0;

        System.out.println("Simple iteration method");
        System.out.println("k" + "\t|\t" + "xk" + "\t\t|\t\t" + "|xk - xk-1|");
        if (q < 1 && Math.abs(x - functionFi(x)) / (1 - q) <= delta) {
            do {
                x = x1;
                x1 = x - Math.signum(function(x)) * q * function(x);
                System.out.println(counter + "\t|\t" + x + "\t|\t" + Math.abs(x1 - x));
                counter++;
            } while (Math.abs(x1 - x) > ACCURACY);
        }
        return x1;
    }

    public static double newtonMethod(double[] interval) {
        double x = (interval[1] + interval[0]) / 2;
        double x1 = x;
        int counter = 0;
        System.out.println("Newton method");
        System.out.println("k" + "\t|\t" + "xk" + "\t\t|\t\t" + "|xk - xk-1|");
        if (function(x) * derivative(x) != 0) {
            do {
                x = x1;
                x1 = x - function(x) / derivative(x);
                System.out.println(counter + "\t|\t" + x + "\t|\t" + Math.abs(x1 - x));
                counter++;
            } while (Math.abs(x1 - x) > ACCURACY);
        }
        return x1;
    }

    public static double chebushevMethod(double[] interval) {
        double x = (interval[1] + interval[0]) / 2;
        double x1 = x;
        int counter = 0;
        System.out.println("Chebushev method");
        System.out.println("k" + "\t|\t" + "xk" + "\t\t|\t\t" + "|xk - xk-1|");
        do {
            x = x1;
            x1 = x - function(x) / derivative(x) - secondDerivative(x) * function(x) * function(x) / (2 * derivative(x) * derivative(x) * derivative(x));
            System.out.println(counter + "\t|\t" + x + "\t|\t" + Math.abs(x1 - x));
            counter++;
        } while (Math.abs(x1 - x) > ACCURACY);
        return x1;
    }

    public static double newtonWithConstantDerivativeMethod(double[] interval) {
        double x = (interval[1] + interval[0]) / 2;
        double x1 = x;
        double derivative = derivative(x);
        int counter = 0;
        System.out.println("Newton with constant derivative method");
        System.out.println("k" + "\t|\t" + "xk" + "\t\t|\t\t" + "|xk - xk-1|");
        if (function(x) * derivative(x) != 0) {
            do {
                x = x1;
                x1 = x - function(x) / derivative;
                System.out.println(counter + "\t|\t" + x + "\t|\t" + Math.abs(x1 - x));
                counter++;
            } while (Math.abs(x1 - x) > ACCURACY);
        }
        return x1;
    }

    public static double steffensenMethod(double[] interval) {
        double x = (interval[1] + interval[0]) / 2;
        double x1 = x;
        int counter = 0;
        System.out.println("Steffensen method");
        System.out.println("k" + "\t|\t" + "xk" + "\t\t|\t\t" + "|xk - xk-1|");
        do {
            x = x1;
            double temp = x - function(x) / derivative(x);
            x1 = temp - function(temp) / derivative(temp);
            System.out.println(counter + "\t|\t" + x + "\t|\t" + Math.abs(x1 - x));
            counter++;
        } while (Math.abs(x1 - x) > ACCURACY);
        return x1;
    }

    public static double secantMethod(double[] interval) {
        double x = (interval[1] + interval[0]) / 2;
        double x1 = x;
        double x2 = x1 - 1;
        int counter = 0;
        System.out.println("Secant method");
        System.out.println("k" + "\t|\t" + "xk" + "\t\t|\t\t" + "|xk - xk-1|");
        do {
            x = x1;
            x1 = x2;
            x2 = x1 - function(x1) * (x1 - x) / (function(x1) - function(x));
            System.out.println(counter + "\t|\t" + x + "\t|\t" + Math.abs(x2 - x1));
            counter++;
        } while (Math.abs(x2 - x1) > ACCURACY);
        return x2;
    }

    public static void main(String[] args) {
        System.out.println("Dichotomy interval: ");
        double[] interval = dichotomyMethod(a, b);
        System.out.println(Arrays.toString(interval));
        double x1 = simpleIterationMethod(interval);
        System.out.println(x1);
        double x2 = newtonMethod(interval);
        System.out.println(x2);
        double x3 = chebushevMethod(interval);
        System.out.println(x3);
        double x4 = newtonWithConstantDerivativeMethod(interval);
        System.out.println(x4);
        double x5 = steffensenMethod(interval);
        System.out.println(x5);
        double x6 = secantMethod(interval);
        System.out.println(x6);
        double average = (x1 + x2 + x3 + x4 + x5 + x6) / 6;
        System.out.println("Difference is: " + Math.abs(x1 - average));
        System.out.println("Difference is: " + Math.abs(x2 - average));
        System.out.println("Difference is: " + Math.abs(x3 - average));
        System.out.println("Difference is: " + Math.abs(x4 - average));
        System.out.println("Difference is: " + Math.abs(x5 - average));
        System.out.println("Difference is: " + Math.abs(x6 - average));
    }
}
