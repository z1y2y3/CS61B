import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        if (items.isEmpty()) {
            return new Queue<>();
        }

        Queue<Queue<Item>> queues = new Queue<>();
        int size = items.size();
        for (Item item : items) {
            Queue<Item> q = new Queue<>();
            q.enqueue(item);
            queues.enqueue(q);
        }
        return queues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> result = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            result.enqueue(getMin(q1, q2));
        }
        return result;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size() <= 1) {
            return items;
        }
        int size = items.size();
        Queue<Queue<Item>> queues = makeSingleItemQueues(items);

        Queue<Item> left = new Queue<>();
        Queue<Item> right = new Queue<>();

        for (int i = 0; i < size / 2; i++) {
            left.enqueue(queues.dequeue().dequeue());
        }
        for (int i = size / 2; i < size; i++) {
            right.enqueue(queues.dequeue().dequeue());
        }

        Queue<Item> leftQueue = mergeSort(left);
        Queue<Item> rightQueue = mergeSort(right);
        return mergeSortedQueues(leftQueue, rightQueue);
    }

    @Test
    public void test() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(9);
        queue.enqueue(5);
        queue.enqueue(3);
        queue.enqueue(2);
        queue.enqueue(8);
        queue.enqueue(7);
        queue.enqueue(6);
        queue.enqueue(4);
        queue.enqueue(1);
        Queue<Integer> queue1 = new Queue<>();
        queue1.enqueue(9);
        queue1.enqueue(5);
        queue1.enqueue(3);
        queue1.enqueue(2);
        queue1.enqueue(8);
        queue1.enqueue(7);
        queue1.enqueue(6);
        queue1.enqueue(4);
        queue1.enqueue(1);

        Queue<Integer> actualQueue = mergeSort(queue);

        Queue<Integer> expectedQueue = new Queue<>();
        for (int i = 1; i <= 9; i++) {
            expectedQueue.enqueue(i);
        }

        assertEquals(expectedQueue.toString(), actualQueue.toString());
        assertEquals(queue1.toString(), queue.toString());
    }

}
