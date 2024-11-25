package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         * In other words, the number of oomages per bucket has to be
         * within the range (N / 50, N / 2.5).
         */
        int N = oomages.size();
        List<Oomage>[] buckets = new List[M];
        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            if (buckets[bucketNum] == null) {
                buckets[bucketNum] = new ArrayList<Oomage>();
            }
            buckets[bucketNum].add(oomage);
        }
        for (List<Oomage> bucket : buckets) {
            if (bucket == null) {
                return false;
            }
            int size = bucket.size();
            if (size <= N / 50 || size > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
