package collectors;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleCollection {
  public static void main(String[] args) {
    List.of("Fred", "Jim", "Sheila", "Bill", "Joe", "Sally", "John", "Tommy")
        .stream()
        .collect(Collectors.groupingBy(s -> s.length()))
        .forEach((k, v) ->
            System.out.printf("%d character words are %s\n", k, v));

    List.of("Fred", "Jim", "Sheila", "Bill", "Joe", "Sally", "John", "Tommy")
        .stream()
        .collect(Collectors.partitioningBy(s -> s.length() > 4))
        .entrySet()
        .forEach(System.out::println);

  }
}
