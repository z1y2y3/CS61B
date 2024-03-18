public class LinkedListDeque<T> {
    private int size;
    private Node sentinel;

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node() {
            this.prev = this;
            this.next = this;
        }

        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    /*
     *add and remove operations must not involve any looping or recursion.
     * A single such operation must take “constant time”,
     * i.e. execution time should not depend on the size of the deque.
     *get must use iteration, not recursion.
     */

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
    }


    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item) {
        size += 1;
        Node reminderFirst = sentinel.next;
        Node first = new Node(item, sentinel, reminderFirst);
        sentinel.next = first;
        reminderFirst.prev = first;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        size += 1;
        Node reminderLast = sentinel.prev;
        Node last = new Node(item, reminderLast, sentinel);
        sentinel.prev = last;
        reminderLast.next = last;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return this.size;
    }


    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        Node ptr = sentinel.next;
        for (int i = 0; i < size(); i++) {
            System.out.print(ptr.value + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        size -= 1;
        Node first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;

        first.prev = null;
        first.next = null;
        return first.value;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        size -= 1;
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;

        last.prev = null;
        last.next = null;
        return last.value;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (size() == 0 || index > size() - 1) {
            return null;
        }
        Node ptr = sentinel.next;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return ptr.value;
            } else {
                ptr = ptr.next;
            }
        }
        return null;
    }

    /**
     * Same as get, but uses recursion.
     */
    private T getHelper(int index, Node ptr) {
        if (index == 0) {
            return ptr.value;
        }
        return getHelper(index - 1, ptr.next);
    }

    public T getRecursive(int index) {
        if (size() == 0 || index > size() - 1) {
            return null;
        }
        return getHelper(index, sentinel.next);
    }
}
