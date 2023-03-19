package defaultresolution;

interface W {
//  default boolean equals(Object o) {return true};

  default void doStuff() {
    System.out.println("default W.doStuff");
  }
}
class B extends A {
  public void doStuff() {
    System.out.println("B.doStuff");
//    W.super.doStuff();
  }
}
class A implements W {
  public void doStuff() {
    System.out.println("A.doStuff");
//    super.doStuff();
  }
}

public class Resolve {
  public static void main(String[] args) {
    new A().doStuff();
  }
}
