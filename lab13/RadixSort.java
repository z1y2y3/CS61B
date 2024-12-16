import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // Implement LSD Sort
        // 获取字符串数组中最长字符串的长度
        int maxLength = 0;
        for (String s : asciis) {
            maxLength = Math.max(maxLength, s.length());
        }

        // 克隆原始数组，保持非破坏性
        String[] sortedArray = Arrays.copyOf(asciis, asciis.length);

        // 从最右侧字符开始排序
        for (int index = maxLength - 1; index >= 0; index--) {
            // 对当前字符位置进行稳定排序
            sortHelperLSD(sortedArray, index);
        }

        return sortedArray;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        // 创建计数数组，ASCII 字符范围是 0 到 127（标准 ASCII 范围）
        int[] count = new int[128 + 1];  // 0 到 127 是 ASCII 字符的范围
        String[] output = new String[asciis.length];

        // 统计每个字符的出现次数
        for (String s : asciis) {
            // 处理越界的情况（如果字符串长度小于当前 index）
            int charIndex = (index < s.length()) ? s.charAt(index) : 0; // 使用 ASCII 值为 0 的字符（空字符）表示未填充的位置
            count[charIndex + 1]++;
        }

        // 修改计数数组，使其包含每个字符的实际位置
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // 反向遍历原始数组，确保排序稳定
        for (int i = asciis.length - 1; i >= 0; i--) {
            String s = asciis[i];
            int charIndex = (index < s.length()) ? s.charAt(index) : 0; // 处理越界字符
            output[count[charIndex] - 1] = s;
            count[charIndex]--;
        }

        // 将排序结果复制回原数组
        System.arraycopy(output, 0, asciis, 0, asciis.length);
    }


    /**
     * Performs MSD radix sort on an array of ASCII Strings.
     * This is a non-destructive method, meaning the original array is not modified.
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] MSDsort(String[] asciis) {
        // 克隆原始数组，保持非破坏性
        String[] sortedArray = Arrays.copyOf(asciis, asciis.length);

        // 从第一个字符开始排序
        sortHelperMSD(sortedArray, 0, asciis.length, 0);

        return sortedArray;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     */
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        if (end <= start + 1) {
            return; // 只有一个元素，无需排序
        }

        // 创建桶来存储每个字符的分组
        int[] count = new int[128 + 1];  // 0 到 127 是 ASCII 字符的范围
        String[] output = new String[end - start];

        // 按照当前字符（index）进行计数
        for (int i = start; i < end; i++) {
            int charIndex = (index < asciis[i].length()) ? asciis[i].charAt(index) : 0; // 处理越界字符
            count[charIndex + 1]++;
        }

        // 将计数数组转换为桶的起始位置
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // 按照当前字符分配字符串到桶中
        for (int i = end - 1; i >= start; i--) {
            int charIndex = (index < asciis[i].length()) ? asciis[i].charAt(index) : 0;
            output[count[charIndex]++] = asciis[i];
        }

        // 将排序后的字符串数组复制回原数组
        System.arraycopy(output, 0, asciis, start, end - start);

        // 对每个桶进行递归排序
        for (int i = 0; i < count.length - 1; i++) {
            if (count[i] < count[i + 1]) {
                sortHelperMSD(asciis, count[i], count[i + 1], index + 1);
            }
        }
    }
}
