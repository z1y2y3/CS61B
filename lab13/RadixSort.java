import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
/*        int numDigitsInAnInteger = 0;

        for (int i = 0; i < asciis.length; i++) {
            if (asciis[i].length() > numDigitsInAnInteger) {
                numDigitsInAnInteger = asciis[i].length();
            }
        }

        String[] sortedAsciis = asciis;

        for (int i = 0; i < numDigitsInAnInteger; i++) {
            sortedAsciis = sortHelperLSD(sortedAsciis, i, numDigitsInAnInteger);
        }
        // print(asciis);
        // print(sortedAsciis);
        return sortedAsciis;*/

        // 克隆原始数组以保持非破坏性
        String[] sortedArray = Arrays.copyOf(asciis, asciis.length);

        // 从第一个字符开始排序
        sortHelperMSD(sortedArray, 0, sortedArray.length, 0);

        return sortedArray;
    }

    private static void print(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            System.out.print(strings[i] + ' ');
        }
        System.out.println();
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index, int numDigitsInAnInteger) {
        ArrayList<String>[] buckets = new ArrayList[256];
        for (int i = 0; i < 256; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (String str : asciis) {
            int charIndex;
            if (numDigitsInAnInteger - index > str.length()) {
                charIndex = 0;
            } else {
                charIndex = (int) str.charAt(numDigitsInAnInteger - index - 1);
            }
            buckets[charIndex].add(str);
        }

        int currentIndex = 0;
        for (ArrayList<String> bucket : buckets) {
            for (String str : bucket) {
                asciis[currentIndex++] = str;
            }
        }

        return asciis;
    }


    public static String[] MSDsort(String[] asciis) {
        // 克隆原始数组以保持非破坏性
        String[] sortedArray = Arrays.copyOf(asciis, asciis.length);

        // 从第一个字符开始排序
        sortHelperMSD(sortedArray, 0, sortedArray.length, 0);

        return sortedArray;
    }

    /*    这个方法用来获取字符串数组中最长字符串的长度
        这个长度用于判断字符索引是否超出了所有字符串的长度 */
    private static int getMaxLength(String[] asciis) {
        int maxLength = 0;
        for (String s : asciis) {
            maxLength = Math.max(maxLength, s.length());
        }
        return maxLength;
    }

    /**
     * 获取指定字符串在给定索引处的字符。
     * 如果索引超出字符串的范围，则返回空字符（ASCII 值为 0）
     */
    private static char getCharacterAt(String str, int index) {
        if (index < str.length()) {
            return str.charAt(index);  // 返回指定索引的字符
        } else {
            return '\0';  // 如果索引超出范围，返回空字符（ASCII 0）
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param arr   String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end   int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] arr, int start, int end, int index) {
        // 基本情况：如果当前范围只有一个元素，或者索引超出了字符串的最大长度，则停止递归
        if (end - start <= 1 || index >= getMaxLength(arr)) {
            return;
        }

        // 创建 128 个桶（对应 ASCII 字符 0 到 127）
        List<String>[] buckets = new ArrayList[128];
        for (int i = 0; i < 128; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 根据当前字符的 ASCII 值，将字符串分配到对应的桶中
        for (int i = start; i < end; i++) {
            char c = getCharacterAt(arr[i], index);  // 获取当前字符串在指定位置的字符
            buckets[c].add(arr[i]);  // 将字符串放入对应的桶中
        }

        // 递归地排序每个桶中的字符串
        int currentIndex = start;
        for (List<String> bucket : buckets) {
            if (!bucket.isEmpty()) {
                // 将排序后的桶中的元素返回到原数组中
                int temp = currentIndex;
                for (String str : bucket) {
                    arr[currentIndex++] = str;
                }
                // 递归地对每个桶中的字符串按照下一个字符的 ASCII 值进行排序
                sortHelperMSD(arr, temp, temp + bucket.size(), index + 1);
            }
        }
    }

}