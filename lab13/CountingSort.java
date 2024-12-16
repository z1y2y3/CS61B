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
        //  make counting sort work with arrays containing negative numbers.
        // 1. 找到最小值和最大值
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            max = max > num ? max : num;
            min = min < num ? min : num;
        }

        // 2. 创建两个计数数组，一个处理负数，一个处理正数
        int[] countNegative = new int[Math.abs(min)];  // 处理负数
        int[] countPositive = new int[max + 1];         // 处理正数

        // 3. 分别计数负数和正数
        for (int num : arr) {
            if (num < 0) {
                countNegative[-num]++;  // 负数使用其绝对值减1作为索引,因为最小负数的绝对值 大于 最大正数
            } else {
                countPositive[num]++;       // 正数直接作为索引
            }
        }

        // 4. 创建一个新的数组来存储排序后的结果
        int[] sortedArr = new int[arr.length];
        int index = 0;

        // 5. 处理负数部分，按照从小到大的顺序输出
        for (int i = countNegative.length - 1; i >= 0; i--) {
            while (countNegative[i] > 0) {
                sortedArr[index++] = -(i + 1); // 恢复原始负数
                countNegative[i]--;
            }
        }

        // 6. 处理正数部分，按照从小到大的顺序输出
        for (int i = 0; i < countPositive.length; i++) {
            while (countPositive[i] > 0) {
                sortedArr[index++] = i; // 恢复原始正数
                countPositive[i]--;
            }
        }

        return sortedArr;  // 返回排序后的新数组
    }
}
