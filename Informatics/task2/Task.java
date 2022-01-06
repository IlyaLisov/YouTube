import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Task {
    private static int[] array1;
    private static int[] array2;

    public static void readArrays() {
        try(Scanner scanner = new Scanner(new File("input.txt"))) {
            int length1 = scanner.nextInt();
            int length2 = scanner.nextInt();
            array1 = new int[length1];
            array2 = new int[length2];

            for(int i = 0; i < length1; i++) {
                array1[i] = scanner.nextInt();
            }

            for(int i = 0; i < length2; i++) {
                array2[i] = scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve() {
        try(PrintWriter writer = new PrintWriter("output.txt")) {
            for(int j : array2) {
                int index = Arrays.binarySearch(array1, j);

                if(index >= 0) {
                    writer.println(array1[index]);
                } else {
                    index = -(index + 1);
                    if(index == 0) {
                        writer.println(array1[0]);
                    } else if(index == array1.length) {
                        writer.println(array1[index - 1]);
                    } else {
                        int d1 = Math.abs(array1[index - 1] - j);
                        int d2 = Math.abs(array1[index] - j);
                        writer.println(d1 <= d2 ? array1[index - 1] : array1[index]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readArrays();
        solve();
    }
}
