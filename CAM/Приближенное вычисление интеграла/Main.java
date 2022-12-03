public class Main {
    private final static double e = 1e-7;
    private final static double a = 0;
    private final static double b = Math.PI / 2;
    private final static int m1 = 2;
    private final static int m2 = 4;

    public static double getF(double x) {
        return x / (1 + Math.cos(x) + x * x);
    }

    public static int getNSP() {
        int N = 1;
        double R2 = 0;
        do {
            N *= 2;
            double Q1 = solveSP(N / 2);
            double Q2 = solveSP(N);
            double h1 = 2 * (b - a) / N;
            double h2 = (b - a) / N;
            R2 = (Q2 - Q1) / (Math.pow(h1 / h2, m1) - 1);
        } while (Math.abs(R2) > e);
        return N;
    }

    public static int getNS() {
        int N = 1;
        double R2 = 0;
        do {
            N *= 2;
            double Q1 = solveS(N / 2);
            double Q2 = solveS(N);
            double h1 = 2 * (b - a) / N;
            double h2 = (b - a) / N;
            R2 = (Q2 - Q1) / (Math.pow(h1 / h2, m2) - 1);
        } while (Math.abs(R2) > e);
        return N;
    }

    public static double solveSP(int N) {
        double result = 0;
        double h = (b - a) / N;
        for (int i = 0; i < N; i++) {
            result += getF(a + h * (i + 0.5));
        }
        return result * h;
    }

    public static double rungeSP() {
        int N = 1;
        double R;
        do {
            N *= 2;
            double Q1 = solveSP(N / 2);
            double Q2 = solveSP(N);
            R = (Q1 - Q2) / (Math.pow(2, m1) - 1);
        } while (Math.abs(R) > e);
        return R;
    }

    public static double solveS(int N) {
        double result = 0;
        double h = (b - a) / N;
        result += getF(a) + getF(b) + 4 * getF(a + h / 2);
        for (int i = 1; i < N; i++) {
            result += 2 * getF(a + h * i);
            result += 4 * getF(a + h * (i + 0.5));
        }
        return result * h / 6;
    }

    public static double rungeS() {
        int N = 1;
        double R;
        do {
            N *= 2;
            double Q1 = solveS(N / 2);
            double Q2 = solveS(N);
            R = (Q1 - Q2) / (Math.pow(2, m2) - 1);
        } while (Math.abs(R) > e);
        return R;
    }

    public static double solveNAST() {
        double result = 0;
        double[] x = {
                -Math.sqrt((35 + 2 * Math.sqrt(70)) / 63),
                -Math.sqrt((35 - 2 * Math.sqrt(70)) / 63),
                0,
                Math.sqrt((35 - 2 * Math.sqrt(70)) / 63),
                Math.sqrt((35 + 2 * Math.sqrt(70)) / 63)};
        double[] y = {
                (322 - 13 * Math.sqrt(70)) / 900,
                (322 + 13 * Math.sqrt(70)) / 900,
                128.0 / 225,
                (322 + 13 * Math.sqrt(70)) / 900,
                (322 - 13 * Math.sqrt(70)) / 900};
        for(int i = 0; i < 5; i++) {
            result += getF((a + b) / 2 + (b - a) / 2 * x[i]) * y[i];
        }
        return result * (b - a) / 2;
    }

    public static void main(String[] args) {
        double answer = 0.469207520130;
        System.out.println("Точное значение: " + answer);
        System.out.println();
        int N1 = getNSP();
        System.out.println("N для КФ средних прямоугольников: " + N1);
        double Q1 = solveSP(N1);
        System.out.println("КФ средних прямоугольников: " + Q1);
        System.out.println("Правило Рунге средних прямоугольников: " + rungeSP());
        System.out.println("Разница с точным значением интеграла: " + Math.abs(answer - Q1));
        System.out.println();
        int N2 = getNS();
        System.out.println("N для КФ Симпсона: " + N2);
        double Q2 = solveS(N2);
        System.out.println("КФ Симпсона: " + Q2);
        System.out.println("Правило Рунге Симпсона: " + rungeS());
        System.out.println("Разница с точным значением интеграла: " + Math.abs(answer - Q2));
        System.out.println();
        double Q3 = solveNAST();
        System.out.println("НАСТ: " + Q3);
        System.out.println("Разница с точным значением интеграла: " + Math.abs(answer - Q3));
    }
}
