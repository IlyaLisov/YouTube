import java.util.Arrays;

public class Main {
    private static final double[] interval = new double[]{-2.0, 2.0};

    public static double function1(double x) {
        return x * x * Math.cos(2 * x);
    }

    public static double function2(double x) {
        return 1.0 / (1 + 5 * x * x);
    }

    public static double[] getX(int n) {
        double[] x = new double[n + 1];
        for (int i = 0; i < n + 1; i++) {
            x[i] = interval[0] + ((interval[1] - interval[0]) / n) * i;
        }
        return x.clone();
    }

    public static double[] getXChebushev(int n) {
        double[] x = new double[n + 1];
        for (int i = 0; i < n + 1; i++) {
            x[i] = (interval[0] + interval[1]) / 2 + (interval[1] - interval[0]) / 2 * Math.cos((2.0 * i + 1) / 2 / (n + 1) * Math.PI);
        }
        return x.clone();
    }

    public static double[] solveWithEquallySpacedNodes(int n, int functionId) {
        double[] x = getX(n);
        double[] y = new double[n + 1];
        if (functionId == 1) {
            for (int i = 0; i < n + 1; i++) {
                y[i] = function1(x[i]);
            }
        } else {
            for (int i = 0; i < n + 1; i++) {
                y[i] = function2(x[i]);
            }
        }
        double[][] matrix = new double[n + 1][n + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.pow(x[i], j);
            }
        }
        return new GaussMethod(matrix, y).solveMatrix();
    }

    public static double[] solveWithChebushevNodes(int n, int functionId) {
        double[] x = getXChebushev(n);
        double[] y = new double[n + 1];
        if (functionId == 1) {
            for (int i = 0; i < n + 1; i++) {
                y[i] = function1(x[i]);
            }
        } else {
            for (int i = 0; i < n + 1; i++) {
                y[i] = function2(x[i]);
            }
        }
        double[][] matrix = new double[n + 1][n + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.pow(x[i], j);
            }
        }
        return new GaussMethod(matrix, y).solveMatrix();
    }

    public static double getAccuracy(double[] coefficients, int functionId) {
        double maxAccuracy = 0.0;
        for (int i = 0; i < 101; i++) {
            double x = (interval[0] + (i * (interval[1] - interval[0]) / 100));

            double actual = 0.0;
            for (int j = 0; j < coefficients.length; j++) {
                actual += coefficients[j] * Math.pow(x, j);
            }
            double expected;
            if (functionId == 1) {
                expected = function1(x);
            } else {
                expected = function2(x);
            }
            double accuracy = Math.abs(actual - expected);

            if (Math.abs(accuracy) > maxAccuracy) {
                maxAccuracy = Math.abs(accuracy);
            }
        }
        return maxAccuracy;
    }

    public static void main(String[] args) {
        System.out.println("Равноотстоящие узлы: ");
        {
            double[] solve15 = solveWithEquallySpacedNodes(5, 1);
            System.out.println("Коэффициенты ИП степени 5 для первой функции: ");
            System.out.println(Arrays.toString(solve15));

            double[] solve110 = solveWithEquallySpacedNodes(10, 1);
            System.out.println("Коэффициенты ИП степени 10 для первой функции: ");
            System.out.println(Arrays.toString(solve110));

            double[] solve115 = solveWithEquallySpacedNodes(15, 1);
            System.out.println("Коэффициенты ИП степени 15 для первой функции: ");
            System.out.println(Arrays.toString(solve115));

            double[] solve120 = solveWithEquallySpacedNodes(20, 1);
            System.out.println("Коэффициенты ИП степени 20 для первой функции: ");
            System.out.println(Arrays.toString(solve120));
            System.out.println();

            double accuracy15 = getAccuracy(solve15, 1);
            System.out.println("Погрешность ИП степени 5 для первой функции: ");
            System.out.println(accuracy15);

            double accuracy110 = getAccuracy(solve110, 1);
            System.out.println("Погрешность ИП степени 10 для первой функции: ");
            System.out.println(accuracy110);

            double accuracy115 = getAccuracy(solve115, 1);
            System.out.println("Погрешность ИП степени 15 для первой функции: ");
            System.out.println(accuracy115);

            double accuracy120 = getAccuracy(solve120, 1);
            System.out.println("Погрешность ИП степени 20 для первой функции: ");
            System.out.println(accuracy120);
            System.out.println();

            double[] solve25 = solveWithEquallySpacedNodes(5, 2);
            System.out.println("Коэффициенты ИП степени 5 для второй функции: ");
            System.out.println(Arrays.toString(solve25));

            double[] solve210 = solveWithEquallySpacedNodes(10, 2);
            System.out.println("Коэффициенты ИП степени 10 для второй функции: ");
            System.out.println(Arrays.toString(solve210));

            double[] solve215 = solveWithEquallySpacedNodes(15, 2);
            System.out.println("Коэффициенты ИП степени 15 для второй функции: ");
            System.out.println(Arrays.toString(solve215));

            double[] solve220 = solveWithEquallySpacedNodes(20, 2);
            System.out.println("Коэффициенты ИП степени 20 для второй функции: ");
            System.out.println(Arrays.toString(solve220));

            double accuracy25 = getAccuracy(solve25, 2);
            System.out.println("Погрешность ИП степени 5 для второй функции: ");
            System.out.println(accuracy25);

            double accuracy210 = getAccuracy(solve210, 2);
            System.out.println("Погрешность ИП степени 10 для второй функции: ");
            System.out.println(accuracy210);

            double accuracy215 = getAccuracy(solve215, 2);
            System.out.println("Погрешность ИП степени 15 для второй функции: ");
            System.out.println(accuracy215);

            double accuracy220 = getAccuracy(solve220, 2);
            System.out.println("Погрешность ИП степени 20 для второй функции: ");
            System.out.println(accuracy220);

        }

        System.out.println();
        System.out.println("Чебышёвские узлы: ");
        {
            double[] solve15 = solveWithChebushevNodes(5, 1);
            System.out.println("Коэффициенты ИП степени 5 для первой функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve15));

            double[] solve110 = solveWithChebushevNodes(10, 1);
            System.out.println("Коэффициенты ИП степени 10 для первой функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve110));

            double[] solve115 = solveWithChebushevNodes(15, 1);
            System.out.println("Коэффициенты ИП степени 15 для первой функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve115));

            double[] solve120 = solveWithChebushevNodes(20, 1);
            System.out.println("Коэффициенты ИП степени 20 для первой функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve120));

            double accuracy15 = getAccuracy(solve15, 1);
            System.out.println("Погрешность ИП степени 5 для первой функции: ");
            System.out.println(accuracy15);

            double accuracy110 = getAccuracy(solve110, 1);
            System.out.println("Погрешность ИП степени 10 для первой функции: ");
            System.out.println(accuracy110);

            double accuracy115 = getAccuracy(solve115, 1);
            System.out.println("Погрешность ИП степени 15 для первой функции: ");
            System.out.println(accuracy115);

            double accuracy120 = getAccuracy(solve120, 1);
            System.out.println("Погрешность ИП степени 20 для первой функции: ");
            System.out.println(accuracy120);
            System.out.println();

            double[] solve25 = solveWithChebushevNodes(5, 2);
            System.out.println("Коэффициенты ИП степени 5 для второй функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve25));

            double[] solve210 = solveWithChebushevNodes(10, 2);
            System.out.println("Коэффициенты ИП степени 10 для второй функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve210));

            double[] solve215 = solveWithChebushevNodes(15, 2);
            System.out.println("Коэффициенты ИП степени 15 для второй функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve215));

            double[] solve220 = solveWithChebushevNodes(20, 2);
            System.out.println("Коэффициенты ИП степени 20 для второй функции по чебышёвским узлам: ");
            System.out.println(Arrays.toString(solve220));

            double accuracy25 = getAccuracy(solve25, 2);
            System.out.println("Погрешность ИП степени 5 для второй функции: ");
            System.out.println(accuracy25);

            double accuracy210 = getAccuracy(solve210, 2);
            System.out.println("Погрешность ИП степени 10 для второй функции: ");
            System.out.println(accuracy210);

            double accuracy215 = getAccuracy(solve215, 2);
            System.out.println("Погрешность ИП степени 15 для второй функции: ");
            System.out.println(accuracy215);

            double accuracy220 = getAccuracy(solve220, 2);
            System.out.println("Погрешность ИП степени 20 для второй функции: ");
            System.out.println(accuracy220);
        }
    }
}
