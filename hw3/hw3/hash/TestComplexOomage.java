package hw3.hash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    private void add5Int(List<Integer> params) {
        params.add(1);
        params.add(0);
        params.add(0);
        params.add(0);
        params.add(0);
    }

    /*  Create a list of Complex Oomages called deadlyList
        that shows the flaw in the hashCode function.*/
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            ArrayList<Integer> params = new ArrayList<>();
            for (int j = 0; j < i; j += 1) {
                add5Int(params);
            }
            params.add(123);
            deadlyList.add(new ComplexOomage(params));
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    @Test
    public void test6params() {
        List<Oomage> oomages = new ArrayList<>();
        ArrayList<Integer> params1 = new ArrayList<>(6);
        ArrayList<Integer> params2 = new ArrayList<>(6);
        ArrayList<Integer> params3 = new ArrayList<>(1);
        params1.add(1);
        params1.add(0);
        params1.add(0);
        params1.add(0);
        params1.add(0); //
        params1.add(5);    //hashcode == 5
        //=------------------------
        params2.add(1);
        params2.add(0);
        params2.add(0);
        params2.add(0);
        params2.add(0); //   hashcode == 6
        params2.add(6);

        params3.add(5);
        oomages.add(new ComplexOomage(params1));
        oomages.add(new ComplexOomage(params2));
        oomages.add(new ComplexOomage(params3));
        assertTrue(!oomages.get(0).equals(oomages.get(1)));
        System.out.println(oomages.get(0).hashCode());
        System.out.println(oomages.get(1).hashCode());
        System.out.println(oomages.get(2).hashCode());
        assertFalse(oomages.get(0).hashCode() == oomages.get(2).hashCode());
    }

    /**
     * Calls tests for SimpleOomage.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
