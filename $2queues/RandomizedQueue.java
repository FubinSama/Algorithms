/* *****************************************************************************
 *  Name: wfb
 *  Date: 2020-01-08
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] data;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        data = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == data.length) resize(data.length * 2);
        data[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniform(size);
        Item item = data[i];
        data[i] = data[size-1];
        size--;
        if (size > 0 && size == data.length / 4) resize(data.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniform(size);
        return data[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void resize(int capacity) {
        Item[] oldData = data;
        data = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            data[i] = oldData[i];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        for (String s: rq) {
            StdOut.println(s);
        }
    }

    private class Node {
        private Item item;
        private Node next;
    }

    private class ListIterator implements Iterator<Item> {

        private int[] order;
        private int index;
        
        private ListIterator() {
            order = new int[size()];
            index = 0;
            for (int i = 0; i < size; i++) {
                order[i] = i;
            }
        }

        public boolean hasNext() {
            return index != size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int i = StdRandom.uniform(index, size);
            Item item = data[order[i]];
            order[i] = order[index++];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
