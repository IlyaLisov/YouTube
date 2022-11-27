public class Main {
    private static final double h = 0.1;
    private static final double[] interval = new double[]{0.0, 3.0};

    public static double function(double x) {
        return x * x * Math.cos(2 * x);
    }

    public static double approximateFunction(double[] coefficients, double x) {
        double sum = 0;
        for(int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    public static double[] solve(int size) {
        double[][] matrix = new double[size + 1][size + 1];
        double[] f = new double[size + 1];
        int N = (int) ((interval[1] - interval[0]) / h);
        for(int i = 0; i < size + 1; i++) {
            for(int j = i; j < size + 1; j++) {
                double sum = 0;
                for (int t = 0; t <= N; t++) {
                    double x = interval[0] + h * t;
                    sum += Math.pow(x, i + j);
                }
                matrix[i][j] = matrix[j][i] = sum;
            }
            for (int t = 0; t <= N; t++) {
                double x = interval[0] + h * t;
                f[i] += Math.pow(x, i) * function(x);
            }
        }
        return new GaussMethod(matrix, f).solveMatrix();
    }

    public static double getAccuracy(double[] coefficients) {
        double sum = 0;
        for(int k = 0; k < (interval[1] - interval[0]) / h; k++) {
            double x = interval[0] + h * k;
            sum += Math.pow(function(x) - approximateFunction(coefficients, x), 2);
        }

        return sum;
    }

    public static void main(String[] args) {
        double[] indices = solve(4);
        System.out.print(indices[0] + " ");
        for(int i = 1; i < indices.length; i++) {
            System.out.print(indices[i] + " * x^" + i + " ");
        }
        System.out.println();
        System.out.println("Accuracy: " + getAccuracy(indices));

        double[] indices2 = solve(6);
        System.out.print(indices2[0] + " ");
        for(int i = 1; i < indices2.length; i++) {
            System.out.print(indices2[i] + " * x^" + i + " ");
        }
        System.out.println();
        System.out.println("Accuracy: " + getAccuracy(indices2));


        double[] indices3 = solve(8);
        System.out.print(indices3[0] + " ");
        for(int i = 1; i < indices3.length; i++) {
            System.out.print(indices3[i] + " * x^" + i + " ");
        }
        System.out.println();
        System.out.println("Accuracy: " + getAccuracy(indices3));

        double[] indices4 = solve(10);
        System.out.print(indices4[0] + " ");
        for(int i = 1; i < indices4.length; i++) {
            System.out.print(indices4[i] + " * x^" + i + " ");
        }
        System.out.println();
        System.out.println("Accuracy: " + getAccuracy(indices4));
    }
}
