import java.io.*;
import java.util.*;

public class TestFileGenerator {
    public static void generateRandomTests(int lengthStart, int lengthEnd, int numberStart, int numberEnd, int numTestsPerFile) throws IOException {
        String fileName = "randomTests.txt";
        Random rand = new Random();

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int t = 0; t < numTestsPerFile; t++) {
                int N = rand.nextInt(lengthEnd - lengthStart + 1) + lengthStart;
                writer.println(N);
                for (int i = 0; i < N; i++) {
                    writer.print(rand.nextInt(numberStart, numberEnd) + " ");
                }
                writer.println();
                writer.println(rand.nextInt(101));
            }
        }

        System.out.println("Random test files is generated: " + fileName);
    }

    // Correct Pairwise implementation based on provided Python logic
    public static void generatePairwiseTests(int numberOfElements, int lowest, int highest) throws IOException {
        Random rand = new Random();

        List<Integer> defaults = new ArrayList<>();
        for (int i = 0; i < numberOfElements; i++) {
            defaults.add(rand.nextInt(highest - lowest + 1) + lowest);
        }

        List<Integer> typicals = new ArrayList<>();
        for (int i = 0; i < numberOfElements; i++) {
            int val;
            do {
                val = rand.nextInt(highest - lowest + 1) + lowest;
            } while (val == defaults.get(i));
            typicals.add(val);
        }

        List<List<Integer>> testCases = new ArrayList<>();
        testCases.add(new ArrayList<>(defaults)); // 0-wise testcase

        // 1-wise testcases
        for (int element = 0; element < numberOfElements; element++) {
            List<Integer> newTestCase = new ArrayList<>(defaults);
            newTestCase.set(element, typicals.get(element));
            testCases.add(newTestCase);
        }

        // 2-wise testcases
        for (int first = 0; first < numberOfElements; first++) {
            for (int second = first + 1; second < numberOfElements; second++) {
                List<Integer> newTestCase = new ArrayList<>(defaults);
                newTestCase.set(first, typicals.get(first));
                newTestCase.set(second, typicals.get(second));
                testCases.add(newTestCase);
            }
        }

        // Write pairwise tests to file
        String fileName = "pairwiseTests.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (List<Integer> testCase : testCases) {
                writer.println(testCase.size());
                for (int val : testCase) {
                    writer.print(val + " ");
                }
                writer.println();
                writer.println(rand.nextInt(highest - lowest + 1) + lowest);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        generateRandomTests(5, 10, 1, 101, 10);
        generatePairwiseTests(6, 1, 1000);
    }
}
