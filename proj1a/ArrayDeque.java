public class ArrayDeque<T> {

    private T[] array;

    private int size;

    private int first; // current first

    private int last; // current last

    public ArrayDeque() {
        size = 0;
        array = (T[]) new Object[8];
        first = 5;
        last = 4;
    }


    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];

        int lengthFromFirst = size - first;

        System.arraycopy(array, first, newArray, capacity / 4, lengthFromFirst);

        if (lengthFromFirst != size) {
            System.arraycopy(array, 0,
                    newArray, capacity / 4 + lengthFromFirst, size - lengthFromFirst);
        }
        array = newArray;
        size = capacity;
    }

    /* make index right*/
    private int makeSure(int i) {
        if (i < 0) {
            i = array.length - 1;
        } else if (i > array.length) {
            i = 0;
        }
        return i;
    }

    /* Adds an item of type T to the front of the deque.*/
    public void addFirst(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        int nextFirst = makeSure(first - 1);
        array[nextFirst] = item;
        first = nextFirst;
        size += 1;
    }


    /* Adds an item of type T to the back of the deque.*/
    public void addLast(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        int nextLast = makeSure(last + 1);
        array[nextLast] = item;
        last = nextLast;
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

    }

    /* Removes and returns the item at the front of the deque.
    If no such item exists, returns null.*/
    public T removeFirst() {
        double usageFactor = (double) size / (double) array.length;
        if (size >= 16 && usageFactor < 0.25f) {
            resize(size / 2);
        }
        int nextFirst = makeSure(first + 1);
        T temp = array[first];
        array[first] = null;
        first = nextFirst;
        size -= 1;
        return temp;
    }

    /* Removes and returns the item at the back of the deque.
    If no such item exists, returns null.*/
    public T removeLast() {
        double usageFactor = (double) size / (double) array.length;
        if (size >= 16 && usageFactor < 0.25f) {
            resize(size / 2);
        }
        int nextLast = makeSure(last - 1);
        T temp = array[nextLast];
        array[nextLast] = null;
        last = nextLast;
        size -= 1;
        return temp;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     If no such item exists, returns null. Must not alter the deque!
     must iteratively
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return array[index];
    }

}
