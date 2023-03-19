package laziness;

import java.util.stream.IntStream;

public class TerminalOps {
  public static void main(String[] args) {
    var c = IntStream.range(1, 10)
        .peek(x -> System.out.println("processing item: " + x))
//        .forEach(System.out::println)
//        .allMatch(x -> x < 4)
        .count()
    ;
    System.out.println(c);
  }
}
