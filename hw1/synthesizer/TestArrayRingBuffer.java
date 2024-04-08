package synthesizer;

import org.junit.Test;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        syntheiszer.ArrayRingBuffer<Integer> x = new syntheiszer.ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            x.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(x.peek());
            x.dequeue();
        }
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
