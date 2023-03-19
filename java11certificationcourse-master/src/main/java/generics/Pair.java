package generics;

import java.time.LocalDate;

public class Pair<E> {  // declares generic type parameter
  private E left; // uses generic type parameter
  private E right;
  public Pair(E left, E right) {
    this.left = left;
    this.right = right;
  }
  public E getLeft() { return left; }
  public void setRight(E right) { this.right = right; }
}

class UsePair {
  public static void main(String[] args) {
    Pair<String> ps = new Pair<>("你好", "Hello");
    String cn = ps.getLeft();
    ps.setRight("Bonjour");
    Pair<LocalDate> pld = new Pair(LocalDate.now(), LocalDate.now());
  }
}
