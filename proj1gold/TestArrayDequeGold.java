import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
    StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> nice = new ArrayDequeSolution<>();

    @Test
    public void testAddFirstRemoveLast() {
        for (int i = 0; i < 10000; i++) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                sad.addFirst(i);
                nice.addFirst(i);
            } else {
                Integer actual = sad.removeLast();
                Integer expected = nice.removeLast();
                assertEquals("expected:" + expected +
                        " but got:" + actual + "\n", expected, actual);
            }
        }
    }

    @Test
    public void testAddLastRemoveFirst() {
        for (int i = 0; i < 10000; i++) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                sad.addLast(i);
                nice.addLast(i);
            } else {
                Integer actual = sad.removeFirst();
                Integer expected = nice.removeFirst();
                assertEquals("expected:" + expected +
                        " but got:" + actual + "\n", expected, actual);
            }
        }
    }


}
