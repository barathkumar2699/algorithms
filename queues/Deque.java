import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {

        private Item item;
        private Node next;
        private Node prev;
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = head;


        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException("Item Ended");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("remove unsupported");
        }
    }

    // construct an empty deque
    Node head = null;
    Node tail = null;
    int n;
    public Deque()
    {
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return n==0;
    }

    // return the number of items on the deque
    public int size()
    {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if(item == null) throw new NullPointerException("Item Cannot be null");
        Node temp = new Node();
        temp.item = item;
        if(head == null)
        {
            head = temp;
            tail = temp;
        }
        else
        {
            temp.next = head;
            head.prev = temp;
            head = temp;
        }

        n++;
        
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if(item == null) throw new NullPointerException("Item Cannot be null");
        Node temp = new Node();
        temp.item = item;
        if(tail == null)
        {
            head = temp;
            tail = temp;
        }
        else
        {
            temp.prev = tail;
            tail.next = temp;
            tail = temp;
        }

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if(head == null) throw new NoSuchElementException("head is empty");
        Node temp = head;
        head = temp.next;
        head.prev = null;
        temp.next = null;
        return null;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = tail.item;
        n--;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(100);
        deque.addLast(101);
        deque.addLast(102);
        Iterator items = deque.iterator();
        while (items.hasNext())
        {
            System.out.println(items.next());
        }
    }

}