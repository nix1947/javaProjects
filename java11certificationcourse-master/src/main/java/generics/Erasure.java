package generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Erasure {
  public static void doStuff1(List<String> ls) {}
  public static <E> void doStuff2(List<E> ls) {}

  public static void main(String[] args) {
    doStuff2(new ArrayList<>(Arrays.asList("Fred")));
  }
}
