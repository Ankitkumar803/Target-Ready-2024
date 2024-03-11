import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TextFileAnalyzer {

    public static void main(String[] args) {
        System.out.print("Enter the filename: ");
        String filename = getInputFileName();

        try {
            String[] lines = readFile(filename);

            System.out.println("Contents of the file:");
            Arrays.stream(lines).forEach(System.out::println);

            displayLongestAndShortestLines(lines);

            int[] wordCounts = countWords(lines);
            displayWordCount(lines, wordCounts);

            sortWordCount(wordCounts);

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the file - " + e.getMessage());
        }
    }

    public static String getInputFileName() {
        return "example.txt";
    }

    public static String[] readFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().toArray(String[]::new);
        }
    }

    public static void displayLongestAndShortestLines(String[] lines) {
        String longestLine = findLongestLine(lines);
        String shortestLine = findShortestLine(lines);

        System.out.println("Longest line:");
        System.out.println(longestLine);

        System.out.println("Shortest line:");
        System.out.println(shortestLine);
    }

    public static String findLongestLine(String[] lines) {
        return Arrays.stream(lines)
                .max(java.util.Comparator.comparingInt(String::length))
                .orElse("");
    }

    public static String findShortestLine(String[] lines) {
        return Arrays.stream(lines)
                .min(java.util.Comparator.comparingInt(String::length))
                .orElse("");
    }

    public static int[] countWords(String[] lines) {
        return Arrays.stream(lines)
                .mapToInt(line -> line.split("\\s+").length)
                .toArray();
    }

    public static void displayWordCount(String[] lines, int[] wordCounts) {
        for (int i = 0; i < lines.length; i++) {
            System.out.println("Line " + (i + 1) + ": " + wordCounts[i] + " words");
        }
    }

    public static void sortWordCount(int[] wordCounts) {
        Arrays.sort(wordCounts);
        reverseArray(wordCounts);

        System.out.println("Sorted word count:");
        Arrays.stream(wordCounts).forEach(count -> System.out.println(count + " words"));
    }

    private static void reverseArray(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }
}
