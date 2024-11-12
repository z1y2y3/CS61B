public class LinkedListDeque<T> {

    private int size;

    private Node sentinel;

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        Node(T item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }

        Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }


    /* Creates an empty linked list deque.*/
    public LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /* Adds an item of type T to the front of the deque.*/
    public void addFirst(T item) {
        if (item == null) {
            return;
        }
        sentinel.next = new Node(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /* Adds an item of type T to the back of the deque.*/
    public void addLast(T item) {
        if (item == null) {
            return;
        }
        sentinel.prev = new Node(item, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /* Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque() {
        Node ptr = sentinel.next;
        while (ptr != sentinel) {
            if (ptr.next == sentinel) {   //last node
                System.out.print(ptr.item);
            } else {
                System.out.print(ptr.item + " ");
            }
            ptr = ptr.next;
        }
    }

    /* Removes and returns the item at the front of the deque.
    If no such item exists, returns null.*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node first = sentinel.next;
        T item = first.item;

        first.next.prev = sentinel;
        sentinel.next = first.next;
        first = null;
        size -= 1;
        return item;
    }

    /* Removes and returns the item at the back of the deque.
    If no such item exists, returns null.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node last = sentinel.prev;
        T item = last.item;

        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        last = null;
        size -= 1;
        return item;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     If no such item exists, returns null. Must not alter the deque!
     must iteratively
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node ptr = sentinel.next;
        for (int i = 0; i < index; i++) {
            ptr = ptr.next;
        }
        return ptr.item;
    }

    private T getRecursiveHelper(int index, Node current) {
        if (index == 0) {
            return current.item;
        }
        return getRecursiveHelper(index - 1, current.next);
    }

    /* Same as get, but uses recursion.*/
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

}
