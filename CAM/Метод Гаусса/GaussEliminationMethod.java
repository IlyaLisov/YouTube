import java.util.Arrays;

public class GaussEliminationMethod {
    private static final int SIZE = 10;
    private static final int[] RANGE = {-100, 100};

    public static double[][] initializeMatrix() {
        double[][] matrix = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = (int) (Math.random() * (RANGE[1] - RANGE[0]) - RANGE[1]);
            }
        }
        return matrix;
    }

    public static double[] getAccurateX() {
        double[] array = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    public static double[] getF(double[][] matrix, double[] x) {
        double[] f = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                f[i] += matrix[i][j] * x[j];
            }
        }
        return f;
    }

    public static void printMatrix(double[][] matrix, double[] f) {
        for (int i = 0; i < SIZE; i++) {
            for (double v : matrix[i]) {
                System.out.printf("%7.2f", v);
            }
            System.out.println(" | " + String.format("%3.0f", f[i]));
        }
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] doubles : matrix) {
            for (double v : doubles) {
                System.out.printf("%8.4f", v);
            }
            System.out.print(System.lineSeparator());
        }
    }

    public static void printMatrix(double[] f) {
        for (double v : f) {
            System.out.println(v);
        }
    }

    public static double[] solveMatrix(double[][] matrix, double[] f) {
        double[][] clonedMatrix = matrix.clone();
        double[] clonedF = f.clone();
        for (int i = 0; i < SIZE; i++) {
            clonedMatrix[i] = matrix[i].clone();
        }

        //straight
        if (noZeroColumns(clonedMatrix)) {
            for (int i = 0; i < SIZE; i++) {
                if (getIndexOfMaxInColumn(clonedMatrix, i) == i) {
                    for (int j = i + 1; j < SIZE; j++) {
                        clonedMatrix[i][j] /= clonedMatrix[i][i];
                    }
                    clonedF[i] /= clonedMatrix[i][i];
                    clonedMatrix[i][i] = 1;

                    for (int j = i + 1; j < SIZE; j++) {
                        for (int k = i + 1; k < SIZE; k++) {
                            clonedMatrix[j][k] -= clonedMatrix[i][k] * clonedMatrix[j][i];
                        }
                        clonedF[j] -= clonedMatrix[j][i] * clonedF[i];
                        clonedMatrix[j][i] = 0;
                    }
                } else {
                    int index = getIndexOfMaxInColumn(clonedMatrix, i);
                    swap(clonedMatrix, index, i);
                    swap(clonedF, index, i);
                    i--;
                }
            }

            //reverse
            for (int i = SIZE - 2; i >= 0; i--) {
                for (int j = SIZE - 1; j > i; j--) {
                    clonedF[i] -= clonedMatrix[i][j] * clonedF[j];
                }
            }
        } else {
            System.out.println("There is zero-column.");
            return null;
        }

        return clonedF;
    }

    public static boolean noZeroColumns(double[][] matrix) {
        for (int j = 0; j < SIZE; j++) {
            int counter = 0;
            for (double[] doubles : matrix) {
                if (doubles[j] == 0) {
                    counter++;
                }
            }
            if (counter == SIZE) {
                return false;
            }
        }
        return true;
    }

    public static int getIndexOfMaxInColumn(double[][] matrix, int column) {
        int maxIndex = 0;
        for (int i = 1; i < SIZE; i++) {
            if (Math.abs(matrix[i][column]) > Math.abs(matrix[maxIndex][column])) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void swap(double[][] matrix, int firstIndex, int secondIndex) {
        double[] temp = matrix[firstIndex];
        matrix[firstIndex] = matrix[secondIndex];
        matrix[secondIndex] = temp;
    }

    public static void swap(double[] f, int firstIndex, int secondIndex) {
        double temp = f[firstIndex];
        f[firstIndex] = f[secondIndex];
        f[secondIndex] = temp;
    }

    public static double getAccuracy(double[] accurateX, double[] calculatedX) {
        double[] accuracy = Arrays.copyOf(accurateX, SIZE);
        for (int i = 0; i < SIZE; i++) {
            accuracy[i] -= calculatedX[i];
        }
        return Math.abs(getMax(accuracy) / getMax(accurateX));
    }

    public static double getMax(double[] array) {
        double max = array[0];
        for (int i = 1; i < SIZE; i++) {
            if (Math.abs(array[i]) > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static double[][] multiplyMatrix(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                double sum = 0;
                for (int k = 0; k < SIZE; k++) {
                    sum += firstMatrix[i][k] * secondMatrix[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public static double getDeterminant(double[][] matrix) {
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        double determinant = 0;
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];
        int multiplier = 1;
        for (int i = 0; i < matrix.length; ++i) {
            int x = 0, y = 0;
            for (int j = 1; j < matrix.length; ++j) {
                for (int k = 0; k < matrix.length; ++k) {
                    if (i == k) {
                        continue;
                    }
                    minor[x][y] = matrix[j][k];
                    ++y;
                    if (y == matrix.length - 1) {
                        y = 0;
                        ++x;
                    }
                }
            }
            determinant += multiplier * matrix[0][i] * getDeterminant(minor);
            multiplier *= (-1);
        }
        return determinant;
    }

    public static double[][] getInverse(double[][] matrix) {
        double[][] clonedMatrix = matrix.clone();
        for (int i = 0; i < SIZE; i++) {
            clonedMatrix[i] = matrix[i].clone();
        }
        double[][] e = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            e[i][i] = 1;
        }

        //straight
        if (getDeterminant(matrix) != 0) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < i + 1; j++) {
                    e[i][j] /= clonedMatrix[i][i];
                }
                for (int j = i + 1; j < SIZE; j++) {
                    clonedMatrix[i][j] /= clonedMatrix[i][i];
                    e[i][j] /= clonedMatrix[i][i];
                }
                clonedMatrix[i][i] = 1;

                for (int j = (i + 1); j < SIZE; j++) {
                    for (int k = 0; k < i + 1; k++) {
                        e[j][k] -= e[i][k] * clonedMatrix[j][i];
                    }
                    for (int k = (i + 1); k < SIZE; k++) {
                        clonedMatrix[j][k] -= clonedMatrix[i][k] * clonedMatrix[j][i];
                        e[j][k] -= e[i][k] * clonedMatrix[j][i];
                    }
                    clonedMatrix[j][i] = 0;
                }
            }

            //reverse
            for (int i = SIZE - 2; i >= 0; i--) {
                for (int j = SIZE - 1; j > i; j--) {
                    for (int k = 0; k < SIZE; k++) {
                        e[i][k] -= clonedMatrix[i][j] * e[j][k];
                    }
                }
            }
        } else {
            System.out.println("This matrix is singular.");
            return null;
        }

        return e;
    }

    public static void main(String[] args) {
        final double[] accurateX = getAccurateX();
        double[][] matrix = initializeMatrix();
        double[] f = getF(matrix, accurateX);
        System.out.println("MATRIX");
        printMatrix(matrix, f);
        System.out.println("EXACT X:");
        printMatrix(accurateX);
        double[] calculatedX = solveMatrix(matrix, f);
        System.out.println("CALCULATED X:");
        printMatrix(calculatedX);
        System.out.println("RELATIVE ACCURACY: " + getAccuracy(accurateX, calculatedX));

        double[][] inverseMatrix = getInverse(matrix);
        System.out.println("INVERSE MATRIX:");
        printMatrix(inverseMatrix);
        double[][] E = multiplyMatrix(inverseMatrix, matrix);
        System.out.println("A^-1 * A:");
        printMatrix(E);
    }
}