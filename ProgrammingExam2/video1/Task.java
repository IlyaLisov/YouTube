import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Task {

    public static int getBandwidth(File file) {
        try(Scanner scanner = new Scanner(file)) {
            int bandwidth = scanner.nextInt();
            return bandwidth;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getText(File file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void solve1(String text, int bandwidth) {
        try(PrintWriter writer = new PrintWriter("output1.txt")) {
            writer.print((int) Math.ceil( 1.0 * text.length() / bandwidth));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve2(String text, int bandwidth) {
        try(PrintWriter writer = new PrintWriter("output2.txt")) {
            int amount = (int) Math.ceil(1.0 * text.length() / bandwidth);
            String fileName = "input1.txt";
            for(int i = amount; i > 1; i--) {
                writer.println(i + "#" + fileName);
            }
            writer.print(1 + "#" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve3(String text, int bandwidth) {
            int amount = (int) Math.ceil(1.0 * text.length() / bandwidth);
            String fileName = "input1.txt";
            for(int i = 1; i <= amount; i++) {
                File file = new File(i + "#" + fileName);
                if(!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String textToFile = text.substring(bandwidth * (i - 1), Math.min(bandwidth * i, text.length()));
                print(file, textToFile);
            }
    }

    public static void print(File file, String text) {
        try(PrintWriter writer = new PrintWriter(file)) {
            writer.print(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve4(String text, int bandwidth) {
        try(PrintWriter writer = new PrintWriter("%input2.txt")) {
            String result = "";
            int amount = (int) Math.ceil(1.0 * text.length() / bandwidth);
            String fileName = "input1.txt";
            for(int i = 1; i <= amount; i++) {
                result += getText(new File(i + "#" + fileName));
            }
            writer.print(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int bandwidth = getBandwidth(new File("input1.txt"));
        String text = getText(new File("input2.txt"));
        solve1(text, bandwidth);
        solve2(text, bandwidth);
        solve3(text, bandwidth);
        solve4(text, bandwidth);
    }
}
