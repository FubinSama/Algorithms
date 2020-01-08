/* *****************************************************************************
 *  Name: wfb
 *  Date: 2020-01-08
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private DNode first;
    private DNode last;
    private int cnt;

    // construct an empty deque
    public Deque() { }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return cnt;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        ++cnt;
        DNode oldFirst = first;
        first = new DNode();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst == null) last = first;
        else oldFirst.prev = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        ++cnt;
        DNode oldLast = last;
        last = new DNode();
        last.item = item;
        last.prev = oldLast;
        if (oldLast == null) first = last;
        else oldLast.next = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        --cnt;
        Item item = first.item;
        DNode next = first.next;
        first.item = null;
        first.next = null;
        this.first = next;
        if (next == null) this.last = null;
        else next.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        --cnt;
        Item item = last.item;
        DNode prev = last.prev;
        last.item = null;
        last.prev = null;
        last = prev;
        if (prev == null) this.first = null;
        else prev.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            dq.addLast(s);
        }
        while (!dq.isEmpty()) {
            StdOut.println(dq.removeFirst());
        }
    }

    private class ListIterator implements Iterator<Item> {

        private DNode cur = first;

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class DNode {
        Item item;
        DNode prev;
        DNode next;
    }
}
