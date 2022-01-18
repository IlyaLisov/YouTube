import java.util.*;

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

    public static int solve7(int[][] matrix) {
        int maxElement = Integer.MIN_VALUE;

        for(int i = 0; i < matrix.length; i++) {
            if(isLineMonotonousUp(matrix, i) || isLineMonotonousDown(matrix, i)) {
                int max = getMaxInLine(matrix, i);
                if(max > maxElement) {
                    maxElement = max;
                }
            }
        }

        return maxElement;
    }

    public static boolean isLineMonotonousUp(int[][] matrix, int index) {
        for(int j = 1; j < matrix.length; j++) {
            if(matrix[index][j] < matrix[index][j - 1]) {
                return false;
            }
        }

        return true;
    }

    public static boolean isLineMonotonousDown(int[][] matrix, int index) {
        for(int j = 1; j < matrix.length; j++) {
            if(matrix[index][j] > matrix[index][j - 1]) {
                return false;
            }
        }

        return true;
    }

    public static int solve8(int[][] matrix) {
        int index = -1;
        int maxLength = 0;

        for(int i = 0; i < matrix.length; i++) {
            int series = getSeries(matrix, i);

            if(series > maxLength) {
                maxLength = series;
                index = i;
            }
        }

        return index;
    }

    public static int getSeries(int[][] matrix, int i) {
        int maxLength = 0;
        for(int j = 0; j < matrix.length; j++) {
            int length = 0;
            for(int k = j; k < matrix.length; k++) {
                if(matrix[i][k] == matrix[i][j]) {
                    length++;
                } else {
                    break;
                }
            }
            if(length > maxLength) {
                maxLength = length;
            }
        }

        return maxLength;
    }

    public static int solve9(int[][] matrix) {
        int index = -1;
        int minSeries = 1;

        for(int i = 0; i < matrix.length; i++) {
            int series = getSeries(matrix, i);
            if(series <= minSeries) {
                minSeries = series;
                index = i;
            }
        }

        return index;
    }

    public static int solve10(int[][] matrix) {
        Map<Integer, Integer> numbers = new TreeMap<>(Collections.reverseOrder());

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(numbers.containsKey(matrix[i][j])) {
                    int count = numbers.get(matrix[i][j]);
                    numbers.put(matrix[i][j], count + 1);
                } else {
                    numbers.put(matrix[i][j], 1);
                }
            }
        }

        for(int i : numbers.keySet()) {
            if(numbers.get(i) > 1) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    public static int solve11(int[][] matrix) {
        Map<Integer, Integer> numbers = new TreeMap<>();

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(numbers.containsKey(matrix[i][j])) {
                    int count = numbers.get(matrix[i][j]);
                    numbers.put(matrix[i][j], count + 1);
                } else {
                    numbers.put(matrix[i][j], 1);
                }
            }
        }

        for(int i : numbers.keySet()) {
            if(numbers.get(i) == 1) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    public static int solve12(int[][] matrix) {
        Map<Integer, Integer> numbers = new TreeMap<>(Collections.reverseOrder());

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(numbers.containsKey(matrix[i][j])) {
                    int count = numbers.get(matrix[i][j]);
                    numbers.put(matrix[i][j], count + 1);
                } else {
                    numbers.put(matrix[i][j], 1);
                }
            }
        }

        for(int i : numbers.keySet()) {
            if(numbers.get(i) == 2) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    public static int solve13(int[][] matrix) {
        int maxSum = 0;

        for(int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for(int j = 0; j < matrix.length - i; j++) {
                sum += matrix[i + j][j];
            }
            if(sum > maxSum) {
                maxSum = sum;
            }
        }

        for(int j = 1; j < matrix.length; j++) {
            int sum = 0;
            for(int i = 0; i < matrix.length - j; i++) {
                sum += matrix[i][i + j];
            }
            if(sum > maxSum) {
                maxSum = sum;
            }
        }

        return maxSum;
    }

    public static int solve14(int[][] matrix) {
        int minSum = Integer.MAX_VALUE;

        for(int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for(int j = 0; j <= i; j++) {
                sum += Math.abs(matrix[i - j][j]);
            }
            if(sum < minSum) {
                minSum = sum;
            }
        }

        for(int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for(int j = matrix.length - 1; j >= i; j--) {
                sum += Math.abs(matrix[i + matrix.length - 1 - j][j]);
            }
            if(sum < minSum) {
                minSum = sum;
            }
        }

        return minSum;
    }

    public static int[][] solve15(int[][] matrix) {
        int[][] result = getMatrixCopy(matrix);

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result.length; j++) {
                if(result[i][0] > result[j][0]) {
                    swapLines(result, i, j);
                }
            }
        }

        return result;
    }

    public static int[][] solve16(int[][] matrix) {
        int[][] result = getMatrixCopy(matrix);

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result.length; j++) {
                if(getLineSum(result, i) < getLineSum(result, j)) {
                    swapLines(result, i, j);
                }
            }
        }

        return result;
    }

    public static int getLineSum(int[][] matrix, int index) {
        int sum = 0;
        for(int i : matrix[index]) {
            sum += i;
        }

        return sum;
    }

    public static int[][] solve17(int[][] matrix) {
        int[][] result = getMatrixCopy(matrix);

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result.length; j++) {
                if(getMaxInLine(result, i) < getMaxInLine(result, j)) {
                    swapLines(result, i, j);
                }
            }
        }

        return result;
    }

    public static int[][] solve18(int[][] matrix) {
        int[][] result = getMatrixCopy(matrix);

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result.length; j++) {
                if(getMinInColumn(result, i) < getMinInColumn(result, j)) {
                    swapColumns(result, i, j);
                }
            }
        }

        return result;
    }

    public static int getMinInColumn(int[][] matrix, int index) {
        int minElement = matrix[0][index];

        for(int i = 0; i < matrix.length; i++) {
            if(matrix[i][index] < minElement) {
                minElement = matrix[i][index];
            }
        }

        return minElement;
    }

    public static void swapColumns(int[][] matrix, int index1, int index2) {
        for(int i = 0; i < matrix.length; i++) {
            int temp = matrix[i][index1];
            matrix[i][index1] = matrix[i][index2];
            matrix[i][index2] = temp;
        }
    }

    public static int[][] solve19(int[][] matrix1, int[][] matrix2) {
        int[][] result = getMatrixCopy(matrix1);

        for(int i = 0; i < matrix1.length; i++) {
            int maxElement = getMaxInLine(matrix2, i);
            for(int j = 0; j < matrix1.length; j++) {
                result[i][j] *= maxElement;
            }
        }

        return result;
    }

    public static int[][] solve20(int[][] matrix1, int[][] matrix2) {
        int[][] result = getMatrixCopy(matrix1);

        for(int j = 0; j < matrix1.length; j++) {
            int multiply = getLineMultiply(matrix2, j);
            for(int i = 0; i < matrix1.length; i++) {
                result[i][j] += multiply;
            }
        }

        return result;
    }

    public static int getLineMultiply(int[][] matrix, int index) {
        int multiply = 1;
        for(int i : matrix[index]) {
            multiply *= i;
        }

        return multiply;
    }

    public static void main(String[] args) {
        int[][] matrix = generateMatrix(3, 5);

//        int[][] matrix = {{1, 1, 1, 2, 3},
//                          {1, 2, 2, 3, 3},
//                          {2, 2, 2, 2, 1},
//                          {1, 2, 3, 4, 5},
//                          {5, 5, 5, 3, 1}};
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
//
//        System.out.println("Task 6:");
//        print(matrix);
//        System.out.println("Result 6:");
//        solve6(matrix);
//
//        System.out.println("Task 7:");
//        print(matrix);
//        System.out.println("Result 7:");
//        int result7 = solve7(matrix);
//        System.out.println(result7);
//
//        System.out.println("Task 8:");
//        print(matrix);
//        System.out.println("Result 8:");
//        int result8 = solve8(matrix);
//        System.out.println(result8);
//
//        System.out.println("Task 9:");
//        print(matrix);
//        System.out.println("Result 9:");
//        int result9 = solve9(matrix);
//        System.out.println(result9);
//
//        System.out.println("Task 10:");
//        print(matrix);
//        System.out.println("Result 10:");
//        int result10 = solve10(matrix);
//        System.out.println(result10);
//
//        System.out.println("Task 11:");
//        print(matrix);
//        System.out.println("Result 11:");
//        int result11 = solve11(matrix);
//        System.out.println(result11);
//
//        System.out.println("Task 12:");
//        print(matrix);
//        System.out.println("Result 12:");
//        int result12 = solve12(matrix);
//        System.out.println(result12);
//
//        System.out.println("Task 13:");
//        print(matrix);
//        System.out.println("Result 13:");
//        int result13 = solve13(matrix);
//        System.out.println(result13);
//
//        System.out.println("Task 14:");
//        print(matrix);
//        System.out.println("Result 14:");
//        int result14 = solve14(matrix);
//        System.out.println(result14);
//
//        System.out.println("Task 15:");
//        print(matrix);
//        System.out.println("Result 15:");
//        int[][] result15 = solve15(matrix);
//        print(result15);
//
//        System.out.println("Task 16:");
//        print(matrix);
//        System.out.println("Result 16:");
//        int[][] result16 = solve16(matrix);
//        print(result16);
//
//        System.out.println("Task 17:");
//        print(matrix);
//        System.out.println("Result 17:");
//        int[][] result17 = solve17(matrix);
//        print(result17);
//
//        System.out.println("Task 18:");
//        print(matrix);
//        System.out.println("Result 18:");
//        int[][] result18 = solve18(matrix);
//        print(result18);
//
//        System.out.println("Task 19:");
//        print(matrix);
//        System.out.println("Result 19:");
//        int[][] result19 = solve19(matrix, matrix);
//        print(result19);

        System.out.println("Task 20:");
        print(matrix);
        System.out.println("Result 20:");
        int[][] result20 = solve20(matrix, matrix);
        print(result20);
    }
}
