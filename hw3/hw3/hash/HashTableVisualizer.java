package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class HashTableVisualizer {

    private static void add5Int(List<Integer> params) {
        params.add(1);
        params.add(0);
        params.add(0);
        params.add(0);
        params.add(0);
    }

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        /* After getting your simpleOomages to spread out
           nicely, be sure to try
           scale = 0.5, N = 2000, M = 100. */

        double scale = 0.5;
        int N = 1000;
        int M = 10;

        HashTableDrawingUtility.setScale(scale);
        List<Oomage> oomies = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
            ArrayList<Integer> params = new ArrayList<>();
            for (int j = 0; j < i; j += 1) {
                add5Int(params);
            }
            params.add(123);
            oomies.add(new ComplexOomage(params));
        }
        visualize(oomies, M, scale);
    }

    public static void visualize(List<Oomage> oomages, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);
        int[] numInBucket = new int[M];
        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFFF) % M;
            double x = HashTableDrawingUtility.xCoord(numInBucket[bucketNumber]);
            numInBucket[bucketNumber] += 1;
            double y = HashTableDrawingUtility.yCoord(bucketNumber, M);
            s.draw(x, y, scale);
        }
    }
} 
