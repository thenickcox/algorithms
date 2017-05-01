public class DequeIterator {
  Deque deque;
  Deque<Item> i;
  public DequeIterator() {
    deque = new Deque<Item>();
    deque.addLast(1);
    deque.addLast(2);
    deque.addLast(3);
  }

  public void run() {
    for (Deque<Item> i : deque) StdOut.println(i);
  }
}
