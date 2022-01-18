public class Task {
    public static int[][] generateMatrix(int size, int range) {
        int[][] matrix = new int[size][size];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                matrix[i][j] = (int) (Math.random() * 2 * range - range);
            }
        }

        return matrix;
    }

    public static void print(int[][] matrix) {
        for(int[] ints : matrix) {
            for(int i : ints) {
                System.out.printf("%3d", i);
            }
            System.out.println();
        }
    }

    public static int[][] solve1(int[][] matrix) {
        int[][] result = getMatrixCopy(matrix);

        int maxIndex = getLineIndexWithMaxElementInMatrix(matrix);
        int minIndex = getLineIndexWithMinElementInMatrix(matrix);

        swapLines(result, maxIndex, minIndex);

        for(int i = 0; i < matrix.length; i++) {
            if(matrix[i][i] == 0) {
                System.out.println("Line " + i + " contains 0 on diagonal. Max element is " + getMaxInLine(matrix, i));
            }
        }

        return result;
    }

    public static int[][] getMatrixCopy(int[][] matrix) {
        int[][] result = matrix.clone();

        for(int i = 0; i < matrix.length; i++) {
            result[i] = matrix[i].clone();
        }

        return result;
    }

    public static int getLineIndexWithMaxElementInMatrix(int[][] matrix) {
        int maxItem = matrix[0][0];
        int index = 0;

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] > maxItem) {
                    maxItem = matrix[i][j];
                    index = i;
                }
            }
        }

        return index;
    }

    public static int getLineIndexWithMinElementInMatrix(int[][] matrix) {
        int minItem = matrix[0][0];
        int index = 0;

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] < minItem) {
                    minItem = matrix[i][j];
                    index = i;
                }
            }
        }

        return index;
    }

    public static void swapLines(int[][] matrix, int index1, int index2) {
        int[] temp = matrix[index1];
        matrix[index1] = matrix[index2];
        matrix[index2] = temp;
    }

    public static int getMaxInLine(int[][] matrix, int index) {
        int max = matrix[index][0];

        for(int i : matrix[index]) {
            if(i > max) {
                max = i;
            }
        }

        return max;
    }

    public static int[][] solve2(int[][] matrix) {
        int[][] result = getMatrixCopy(matrix);

        if(isMatrixSymmetric(matrix)) {
            System.out.println("Matrix is symmetric.");
        } else {
            System.out.println("Matrix isn`t symmetric.");
        }

        int maxElement = matrix[0][0];
        int indexI = 0;
        int indexJ = 0;

        for(int i = 0; i < matrix.length; i++) {
            if(matrix[i][i] > maxElement) {
                maxElement = matrix[i][i];
                indexI = i;
                indexJ = i;
            }
            if(matrix[i][matrix.length - 1 - i] > maxElement) {
                maxElement = matrix[i][matrix.length - 1 - i];
                indexI = i;
                indexJ = matrix.length - 1 - i;
            }
        }

        result[indexI][indexJ] = matrix[result.length / 2][result.length / 2];
        result[result.length / 2][result.length / 2] = maxElement;

        return result;
    }

    public static boolean isMatrixSymmetric(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < i; j++) {
                if(matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void solve3(int[][] matrix) {
        for(int j = 0; j < matrix.length; j++) {
            if(isColumnEven(matrix, j)) {
                System.out.println("Column " + j + " is even.");
            }

            if(matrix[j][j] < 0) {
                System.out.println("Column " + j + " has negative element on diagonal. Sum of column is " + getColumnSum(matrix, j));
            }
        }
    }

    public static boolean isColumnEven(int[][] matrix, int index) {
        for(int i = 0; i < matrix.length; i++) {
            if(matrix[i][index] % 2 != 0) {
                return false;
            }
        }

        return true;
    }

    public static int getColumnSum(int[][] matrix, int index) {
        int sum = 0;
        for(int i = 0; i < matrix.length; i++) {
            sum += matrix[i][index];
        }

        return sum;
    }

    public static int solve4(int[][] matrix) {
        int index = -1;
        int sum = 0;

        for(int i = 0; i < matrix.length; i++) {
            if(isLineOdd(matrix, i)) {
                int sumI = getLineAbsSum(matrix, i);
                if(sumI > sum) {
                    sum = sumI;
                    index = i;
                }
            }
        }

        return index;
    }

    public static boolean isLineOdd(int[][] matrix, int index) {
        for(int i : matrix[index]) {
            if(i % 2 == 0) {
                return false;
            }
        }

        return true;
    }

    public static int getLineAbsSum(int[][] matrix, int index) {
        int sum = 0;

        for(int i : matrix[index]) {
            sum += Math.abs(i);
        }

        return sum;
    }

    public static int solve5(int[][] matrix, int n) {
        int index = -1;
        int maxMultiply = Integer.MIN_VALUE;

        for(int j = 0; j < matrix.length; j++) {
            if(isColumnLessOrEquals(matrix, j, n)) {
                int multiply = getColumnMultiply(matrix, j);
                if(multiply > maxMultiply) {
                    maxMultiply = multiply;
                    index = j;
                }
            }
        }

        return index;
    }

    public static boolean isColumnLessOrEquals(int[][] matrix, int index, int n) {
        for(int i = 0; i < matrix.length; i++) {
            if(Math.abs(matrix[i][index]) > n) {
                return false;
            }
        }

        return true;
    }

    public static int getColumnMultiply(int[][] matrix, int index) {
        int multiply = 1;

        for(int i = 0; i < matrix.length; i++) {
            multiply *= matrix[i][index];
        }

        return multiply;
    }

    public static void solve6(int[][] matrix) {
        for(int j = 0; j < matrix.length; j++) {
            if(isColumnMonotonousUp(matrix, j) || isColumnMonotonousDown(matrix, j)) {
                System.out.println("Column " + j + " is monotonous.");
            }
        }
    }

    public static boolean isColumnMonotonousUp(int[][] matrix, int index) {
        for(int i = 1; i < matrix.length; i++) {
            if(matrix[i][index] < matrix[i - 1][index]) {
                return false;
            }
        }

        return true;
    }

    public static boolean isColumnMonotonousDown(int[][] matrix, int index) {
        for(int i = 1; i < matrix.length; i++) {
            if(matrix[i][index] > matrix[i - 1][index]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[][] matrix = generateMatrix(5, 10);
//        int[][] matrix = {{1, 2, 3}, {2, 3, 1}, {3, 1, 4}};

//        System.out.println("Task 1:");
//        print(matrix);
//        System.out.println("Result 1:");
//        int[][] result1 = solve1(matrix);
//        print(result1);
//
//        System.out.println("Task 2:");
//        print(matrix);
//        System.out.println("Result 2:");
//        int[][] result2 = solve2(matrix);
//        print(result2);
//
//        System.out.println("Task 3:");
//        print(matrix);
//        System.out.println("Result 3:");
//        solve3(matrix);
//
//        System.out.println("Task 4:");
//        print(matrix);
//        System.out.println("Result 4:");
//        int result4 = solve4(matrix);
//        System.out.println(result4);
//
//        System.out.println("Task 5:");
//        print(matrix);
//        System.out.println("Result 5:");
//        int result5 = solve5(matrix, 8);
//        System.out.println(result5);

        System.out.println("Task 6:");
        print(matrix);
        System.out.println("Result 6:");
        solve6(matrix);
    }
}
