import java.util.Scanner;

public class Task {

    public static double solve1(double x, double accuracy) {
        double result = 1;
        int k = 1;
        double sum = x;
        while(Math.abs(sum) >= accuracy) {
            result += sum;
            sum *= x / (k + 1);
            k++;
        }
        return result;
    }

    public static double getActual1(double x) {
        return Math.exp(x);
    }

    public static double solve2(double x, double accuracy) {
        double result = 1;
        int k = 1;
        double sum = (-1.0) * (k + 1) * (k + 2) / 2 * x;
        while(Math.abs(sum) >= accuracy) {
            result += sum;
            sum *= (-1.0) * (k + 3) / (k + 1) * x;
            k++;
        }
        return result;
    }

    public static double getActual2(double x) {
        return 1 / Math.pow(1.0 + x, 3);
    }

    public static double solve3(double x, double accuracy) {
        double result = -x;
        int k = 1;
        double sum = -(x * x) / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= x * (k + 1) / (k + 2);
            k++;
        }
        return result;
    }

    public static double getActual3(double x) {
        return Math.log(1.0 - x);
    }

    public static double solve4(double x, double accuracy) {
        double result = 1;
        int k = 2;
        double sum = x / k;
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= (-1.0) * x * (k - 1) / (k + 2);
            k += 2;
        }
        return result;
    }

    public static double getActual4(double x) {
        return Math.sqrt(1.0 + x);
    }

    public static double solve5(double x, double accuracy) {
        double result = 1;
        int k = 2;
        double sum = -x / k;
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= (-1.0) * x * (k + 1) / (k + 2);
            k += 2;
        }
        return result;
    }

    public static double getActual5(double x) {
        return 1 / Math.sqrt(1.0 + x);
    }

    public static double solve6(double x, double accuracy) {
        double result = x;
        int k = 2;
        double sum = -(x * x * x) / k / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= (-1.0) * x * x / (2 * k + 1) / (2 * k);
            k++;
        }
        return result;
    }

    public static double getActual6(double x) {
        return Math.sin(x);
    }

    public static double solve7(double x, double accuracy) {
        double result = 1;
        int k = 2;
        double sum = -(x * x) / k;
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= (-1.0) * x * x / (2 * k - 1) / (2 * k);
            k++;
        }
        return result;
    }

    public static double getActual7(double x) {
        return Math.cos(x);
    }

    public static double solve8(double x, double accuracy) {
        double result = x;
        int k = 2;
        double sum = x * x * x / k / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= x * x * (k + 1) * (k + 1) / (k + 2) / (k + 3);
            k += 2;
        }
        return result;
    }

    public static double getActual8(double x) {
        return Math.asin(x);
    }

    public static double solve9(double x, double accuracy) {
        double result = 1;
        int k = 2;
        double sum = -(x * x) / k / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= (-1.0) * x * x / (2 * k + 1) / (2 * k);
            k++;
        }
        return result;
    }

    public static double getActual9(double x) {
        return Math.sin(x) / x;
    }

    public static double solve10(double x, double accuracy) {
        double result = x;
        int k = 2;
        double sum = -(x * x * x) / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= (-1.0) * x * x * (k + 1) / (k + 3);
            k += 2;
        }
        return result;
    }

    public static double getActual10(double x) {
        return Math.atan(x);
    }

    public static double solve11(double x, double accuracy) {
        double result = x;
        int k = 1;
        double sum = -(x * x) / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= (-1.0) * x * (k + 1) / (k + 2);
            k++;
        }
        return result;
    }

    public static double getActual11(double x) {
        return Math.log(1 + x);
    }

    public static double solve12(double x, double accuracy) {
        double result = x;
        int k = 2;
        double sum = x * x * x / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= x * x * (k + 1) / (k + 3);
            k += 2;
        }
        return 2 * result;
    }

    public static double getActual12(double x) {
        return Math.log((1 + x) / (1 - x));
    }

    public static double solve13(double x, double accuracy) {
        double result = x;
        int k = 2;
        double sum = x * x * x / k / (k + 1);
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= x * x / (k + 2) / (k + 3);
            k += 2;
        }
        return result;
    }

    public static double getActual13(double x) {
        return Math.sinh(x);
    }

    public static double solve14(double x, double accuracy) {
        double result = 1;
        int k = 2;
        double sum = x * x / k;
        while(Math.abs(sum) > accuracy) {
            result += sum;
            sum *= x * x / (2 * k - 1) / (2 * k);
            k++;
        }
        return result;
    }

    public static double getActual14(double x) {
        return Math.cosh(x);
    }

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            int k = scanner.nextInt();
            double accuracy = Math.pow(10, -k);

            double x1 = 3.5;
            double x2 = 0.42;

            System.out.println("1. " + (solve1(x1, accuracy) - getActual1(x1)));
            System.out.println("2. " + (solve2(x2, accuracy) - getActual2(x2)));
            System.out.println("3. " + (solve3(x2, accuracy) - getActual3(x2)));
            System.out.println("4. " + (solve4(x2, accuracy) - getActual4(x2)));
            System.out.println("5. " + (solve5(x2, accuracy) - getActual5(x2)));
            System.out.println("6. " + (solve6(x1, accuracy) - getActual6(x1)));
            System.out.println("7. " + (solve7(x1, accuracy) - getActual7(x1)));
            System.out.println("8. " + (solve8(x2, accuracy) - getActual8(x2)));
            System.out.println("9. " + (solve9(x1, accuracy) - getActual9(x1)));
            System.out.println("10. " + (solve10(x2, accuracy) - getActual10(x2)));
            System.out.println("11. " + (solve11(x2, accuracy) - getActual11(x2)));
            System.out.println("12. " + (solve12(x2, accuracy) - getActual12(x2)));
            System.out.println("13. " + (solve13(x1, accuracy) - getActual13(x1)));
            System.out.println("14. " + (solve14(x1, accuracy) - getActual14(x1)));
        }
    }

}
