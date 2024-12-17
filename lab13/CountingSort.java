import java.util.Arrays;

/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        if (arr.length == 0) {
            return arr;
        }

        int max = arr[0];
        int min = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
            if (num < min) min = num;
        }

        int[] count = new int[max - min + 1];

        for (int num : arr) {
            count[num - min]++;
        }

        int[] sorted = new int[arr.length];
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                sorted[index++] = i + min;
                count[i]--;
            }
        }

        return sorted;
    }

    public static void main(String[] args) {
        // 测试用例 1: 包含 Integer.MIN_VALUE 和 Integer.MAX_VALUE
        int[] arr1 = {Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1, -1};
        System.out.println("测试用例 1: 包含 Integer.MIN_VALUE 和 Integer.MAX_VALUE");
        System.out.println("原始数组: " + Arrays.toString(arr1));
        int[] sortedArr1 = CountingSort.betterCountingSort(arr1);
        System.out.println("排序后的数组: " + Arrays.toString(sortedArr1));
        System.out.println();

        // 测试用例 2: 正常情况，包含负数和正数
        int[] arr2 = {10, -5, 3, -10, 8, 0, -3, 5};
        System.out.println("测试用例 2: 正常情况，包含负数和正数");
        System.out.println("原始数组: " + Arrays.toString(arr2));
        int[] sortedArr2 = CountingSort.betterCountingSort(arr2);
        System.out.println("排序后的数组: " + Arrays.toString(sortedArr2));
        System.out.println();

        // 测试用例 3: 全为相同的元素
        int[] arr3 = {7, 7, 7, 7, 7, 7};
        System.out.println("测试用例 3: 全为相同的元素");
        System.out.println("原始数组: " + Arrays.toString(arr3));
        int[] sortedArr3 = CountingSort.betterCountingSort(arr3);
        System.out.println("排序后的数组: " + Arrays.toString(sortedArr3));
        System.out.println();

        // 测试用例 4: 空数组
        int[] arr4 = {};
        System.out.println("测试用例 4: 空数组");
        System.out.println("原始数组: " + Arrays.toString(arr4));
        int[] sortedArr4 = CountingSort.betterCountingSort(arr4);
        System.out.println("排序后的数组: " + Arrays.toString(sortedArr4));
        System.out.println();

        // 测试用例 5: 单一元素数组
        int[] arr5 = {3};
        System.out.println("测试用例 5: 单一元素数组");
        System.out.println("原始数组: " + Arrays.toString(arr5));
        int[] sortedArr5 = CountingSort.betterCountingSort(arr5);
        System.out.println("排序后的数组: " + Arrays.toString(sortedArr5));
    }
}
