package generics;

import java.time.temporal.TemporalAdjuster;

public class OrderedPair<E extends Comparable<E>> {
  private E left, right;

  public OrderedPair(E left, E right) {
    this.left = left;
    this.right = right;
  }

  public void order() {
    if (left.compareTo(right) > 0) {
      E temp = left;
      left = right;
      right = temp;
    }
  }


  public static void main(String[] args) {
    OrderedPair<String> ops = new OrderedPair<>("Alice", "Bob");
    OrderedPair<StringBuilder> opsb = new OrderedPair<>(
        new StringBuilder("Alice"), new StringBuilder("Bob"));
//    OrderedPair<TemporalAdjuster> opta;
  }

}
