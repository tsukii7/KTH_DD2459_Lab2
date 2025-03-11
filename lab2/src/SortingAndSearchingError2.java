import java.util.Arrays;

public class SortingAndSearchingError2 {

    // (i) Sorting: Merge Sort Algorithm
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        mergeAndSort(arr, 0, arr.length - 1);
    }

    private static void mergeAndSort(int[] arr, int left, int right) {
        // Error: The base case should be left < right
        if (left <= right) {
            int mid = left + (right - left) / 2;
            mergeAndSort(arr, left, mid);
            mergeAndSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        int[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            arr[k++] = (leftArr[i] <= rightArr[j]) ? leftArr[i++] : rightArr[j++];
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }

    // (ii) Membership query using Binary Search on sorted arrays
    public static boolean binarySearch(int[] sortedArr, int key) {
        int left = 0, right = sortedArr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (sortedArr[mid] == key) return true;
            if (sortedArr[mid] < key) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }

    // (iii) Membership query on unsorted arrays (Sorting + Binary Search)
    public static boolean searchUnsortedArray(int[] arr, int key) {
        mergeSort(arr);  // Sort the array first
        return binarySearch(arr, key);  // Perform Binary Search
    }
}
