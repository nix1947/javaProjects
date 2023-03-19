package comparatordelegation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReverseOrdering {
  public static <E> Comparator<E>
  backwardsComparator(Comparator<E> forwards) {
    return (a, b) -> forwards.compare(b, a);
  }

  public static void main(String[] args) {
    List<String> names = new ArrayList<>(
        List.of("Fred", "Jim", "Sheila", "Alice", "Bob", "Scott", "Maverick")
    );
    System.out.println(names);
    names.sort(String.CASE_INSENSITIVE_ORDER);
    System.out.println(names);
    names.sort(backwardsComparator(String.CASE_INSENSITIVE_ORDER));
    System.out.println(names);
  }
}
