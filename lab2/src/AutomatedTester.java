import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class AutomatedTester {
    public static void main(String[] args) throws Exception {
//        Scanner scanner = new Scanner(new File("randomTests.txt"));
        Scanner scanner = new Scanner(new File("pairwiseTests.txt"));

        ArrayList<Boolean> correctResults = new ArrayList<>(),
                error1Results = new ArrayList<>(),
                error2Results = new ArrayList<>(),
                error3Results = new ArrayList<>(),
                error4Results = new ArrayList<>(),
                error5Results = new ArrayList<>(),
                error6Results = new ArrayList<>();
        boolean[] throwError = new boolean[7];
        int[] caseIndex = new int[] {-1, -1, -1, -1, -1, -1, -1};
        int idx = 0;
        while (scanner.hasNextLine()) {
            idx++;
            System.out.println("Test case: " + idx);
            int N = Integer.parseInt(scanner.nextLine().trim());
            int[] array = Arrays.stream(scanner.nextLine().trim().split("\\s+"))
                                .mapToInt(Integer::parseInt).toArray();
            int key = Integer.parseInt(scanner.nextLine());

            boolean correctResult = SortingAndSearching.searchUnsortedArray(array, key),
                    error1Result,
                    error2Result,
                    error3Result,
                    error4Result,
                    error5Result,
                    error6Result;

            if (!throwError[1]) {
                try {
                    error1Result = SortingAndSearchingError1.searchUnsortedArray(array, key);
                } catch (
                        Error |
                        Exception e) {
                    error1Result = false;
                    throwError[1] = true;
                    caseIndex[1] = idx;
                }
            } else {
                error1Result = false;
            }

            if (!throwError[2]) {
                try {
                    error2Result = SortingAndSearchingError2.searchUnsortedArray(array, key);
                } catch (
                        Error |
                        Exception e) {
                    error2Result = false;
                    throwError[2] = true;
                    caseIndex[2] = idx;
                }
            } else {
                error2Result = false;
            }

            if (!throwError[3]) {
                try {
                    error3Result = SortingAndSearchingError3.searchUnsortedArray(array, key);
                } catch (
                        Error |
                        Exception e) {
                    error3Result = false;
                    throwError[3] = true;
                    caseIndex[3] = idx;
                }
            } else {
                error3Result = false;
            }

            if (!throwError[4]) {
                try {
                    error4Result = SortingAndSearchingError4.searchUnsortedArray(array, key);
                } catch (
                        Error |
                        Exception e) {
                    error4Result = false;
                    throwError[4] = true;
                    caseIndex[4] = idx;
                }
            } else {
                error4Result = false;
            }

            if (!throwError[5]) {
                try {
                    error5Result = SortingAndSearchingError5.searchUnsortedArray(array, key);
                } catch (
                        Error |
                        Exception e) {
                    error5Result = false;
                    throwError[5] = true;
                    caseIndex[5] = idx;
                }
            } else {
                error5Result = false;
            }

            if (!throwError[6]) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<Boolean> future = executor.submit(() -> SortingAndSearchingError6.searchUnsortedArray(array, key));
                try {
                    error6Result = future.get(1, TimeUnit.SECONDS);
                } catch (
                        TimeoutException e) {
                    future.cancel(true);
                    error6Result = false;
                    throwError[6] = true;
                    System.out.println("error6 - Timeout");
                } catch (
                        ExecutionException |
                        InterruptedException e) {
                    error6Result = false;
                    throwError[6] = true;
                    System.out.println("error6 - Exception");
                } finally {
                    executor.shutdownNow();
                    caseIndex[6] = idx;
                }
            } else {
                error6Result = false;
            }

            correctResults.add(correctResult);
            error1Results.add(error1Result);
            error2Results.add(error2Result);
            error3Results.add(error3Result);
            error4Results.add(error4Result);
            error5Results.add(error5Result);
            error6Results.add(error6Result);
        }

        boolean findError1 = throwError[1] || !IntStream.range(0, correctResults.size()).allMatch(i -> correctResults.get(i) == error1Results.get(i)),
                findError2 = throwError[2] || !IntStream.range(0, correctResults.size()).allMatch(i -> correctResults.get(i) == error2Results.get(i)),
                findError3 = throwError[3] || !IntStream.range(0, correctResults.size()).allMatch(i -> correctResults.get(i) == error3Results.get(i)),
                findError4 = throwError[4] || !IntStream.range(0, correctResults.size()).allMatch(i -> correctResults.get(i) == error4Results.get(i)),
                findError5 = throwError[5] || !IntStream.range(0, correctResults.size()).allMatch(i -> correctResults.get(i) == error5Results.get(i)),
                findError6 = throwError[6] || !IntStream.range(0, correctResults.size()).allMatch(i -> correctResults.get(i) == error6Results.get(i));

        if (!throwError[1] && findError1) {
            caseIndex[1] = IntStream.range(0, correctResults.size()).filter(i -> correctResults.get(i) != error1Results.get(i)).findFirst().orElse(-1);
            caseIndex[1]++;
        }
        if (!throwError[2] && findError2) {
            caseIndex[2] = IntStream.range(0, correctResults.size()).filter(i -> correctResults.get(i) != error2Results.get(i)).findFirst().orElse(-1);
            caseIndex[2]++;
        }
        if (!throwError[3] && findError3) {
            caseIndex[3] = IntStream.range(0, correctResults.size()).filter(i -> correctResults.get(i) != error3Results.get(i)).findFirst().orElse(-1);
            caseIndex[3]++;
        }
        if (!throwError[4] && findError4) {
            caseIndex[4] = IntStream.range(0, correctResults.size()).filter(i -> correctResults.get(i) != error4Results.get(i)).findFirst().orElse(-1);
            caseIndex[4]++;
        }
        if (!throwError[5] && findError5) {
            caseIndex[5] = IntStream.range(0, correctResults.size()).filter(i -> correctResults.get(i) != error5Results.get(i)).findFirst().orElse(-1);
            caseIndex[5]++;
        }
        if (!throwError[6] && findError6) {
            caseIndex[6] = IntStream.range(0, correctResults.size()).filter(i -> correctResults.get(i) != error6Results.get(i)).findFirst().orElse(-1);
            caseIndex[6]++;
        }

        System.out.printf("Error 1: %s\n", findError1 ? "Found in " + caseIndex[1] : "Not Found");
        System.out.printf("Error 2: %s\n", findError2 ? "Found in " + caseIndex[2] : "Not Found");
        System.out.printf("Error 3: %s\n", findError3 ? "Found in " + caseIndex[3] : "Not Found");
        System.out.printf("Error 4: %s\n", findError4 ? "Found in " + caseIndex[4] : "Not Found");
        System.out.printf("Error 5: %s\n", findError5 ? "Found in " + caseIndex[5] : "Not Found");
        System.out.printf("Error 6: %s\n", findError6 ? "Found in " + caseIndex[6] : "Not Found");

        scanner.close();
    }
}
