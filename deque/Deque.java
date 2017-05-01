import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private Deque<Item> deque;
  private Node first = null;
  private Node last = null;
  private int size;
  private Item i;

  private class Node {
    Item item;
    Node next;
    Node previous;
  }

  public Deque() {
    this.size = 0;
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  public int size() {
    return this.size;
  }

  public void addFirst(Item item) {
    if (item == null) throw new java.lang.NullPointerException();
    Node oldFirst = first;
    first = new Node();
    first.item = item;
    first.next = oldFirst;
    this.size++;
  }


  public void addLast(Item item) {
    if (item == null) throw new java.lang.NullPointerException();
    Node oldLast = last;
    last = new Node();
    last.item = item;
    last.previous = oldLast;
    this.size++;
  }

  public Item removeFirst() {
    if (this.size == 0) throw new java.util.NoSuchElementException();
    Node oldFirst = first;
    first = oldFirst.next;
    oldFirst.next = null;
    Item oldItem = oldFirst.item;
    oldFirst.item = null;
    --this.size;
    return oldItem;
  }

  public Item removeLast() {
    if (this.size == 0) throw new java.util.NoSuchElementException();
    Node oldLast = last;
    last = oldLast.previous;
    oldLast.previous = null;
    Item oldItem = oldLast.item;
    oldLast.item = null;
    --this.size;
    return oldItem;
  }

  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;
    public boolean hasNext() { return current != null; }
    public void remove() { throw new java.lang.UnsupportedOperationException(); }
    public Item next() {
      Item item = current.item;
      if (current.next == null)  throw new java.util.NoSuchElementException();
      current = current.next;
      return item;
    }
  }

  public static void main (String[] args) {
    Deque<Integer> deque = new Deque<>();
    deque.addLast(1);
    deque.addLast(2);
    deque.addLast(3);
    StdOut.println("iterating…");
    for (int i : deque) StdOut.println(i);
  }
}
